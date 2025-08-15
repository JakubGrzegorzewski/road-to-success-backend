package grzegorzewski.roadtosuccesbackend.Document.Write;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class AdvancementDocument {
    private AdvDocData userData;
    private final float IDEA_TEXT_WIDTH = 450f;
    private int PAGE_NUMBER = 1;

    private final PDDocument document;
    private final PDFont boldFont;
    private final PDFont mediumFont;
    private final PDFont lightFont;

    public AdvancementDocument(AdvDocData userData) throws IOException {
        document = new PDDocument();
        boldFont = PDType0Font.load(document, new java.io.File("src/main/resources/fonts/Museo-700.ttf"));
        mediumFont = PDType0Font.load(document, new java.io.File("src/main/resources/fonts/Museo-500.ttf"));
        lightFont = PDType0Font.load(document, new java.io.File("src/main/resources/fonts/Museo-300.ttf"));

        this.userData = userData;
    }

    public void generateDocument(ByteArrayOutputStream outputStream) throws IOException {
        PDPage firstPage = createFirstPage(userData);
        document.addPage(firstPage);

        if (userData.getIdea() != null) {
            document.addPage(createSecondPage());
        }

        for (AdvDocTask task : userData.getTasks()) {
            document.addPage(createTaskPages(task));
        }

        document.save(outputStream);
        document.close();
    }

    private PDPage createSecondPage() throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        try (PDPageContentStream content = new PDPageContentStream(document, page)) {
            addBackground(content, page);

            content.beginText();

            float previousX = 20;

            // Idea section title
            String ideaTitle = "Idea stopnia";
            content.setFont(boldFont, 24);
            content.setNonStrokingColor(Color.decode(userData.getThemeColor()));
            float ideaTitleX = CustomPDFMethods.getNextLineCenterOffset(ideaTitle, boldFont, 24, page);
            content.newLineAtOffset(ideaTitleX - previousX, page.getArtBox().getHeight() - 200);
            content.showText(ideaTitle);
            previousX = ideaTitleX;

            // Idea text
            content.setFont(lightFont, 14);
            content.setNonStrokingColor(Color.BLACK);

            List<String> lines = CustomPDFMethods.splitTextIntoLines(userData.getIdea(), lightFont, 14, IDEA_TEXT_WIDTH);
            String longestLine = findLongestLine(lines);
            float ideaTextX = CustomPDFMethods.getNextLineCenterOffset(longestLine, lightFont, 14, page);

            content.newLineAtOffset(ideaTextX - previousX, -30);
            drawJustifiedText(content, lines, lightFont, 14, IDEA_TEXT_WIDTH);

            content.endText();

            addFooter(content, page, PAGE_NUMBER++);
        } catch (Exception e) {
            throw new IOException("Failed to create PDF page", e);
        }
        return page;
    }

    private PDPage createFirstPage(AdvDocData data) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);

        try (PDPageContentStream content = new PDPageContentStream(document, page)) {
            addBackground(content, page);
            drawCenteredImage(content, page, data.getImagePath(), 120, new Vector2D(-20,170));
            drawContent(content, page, data, new Vector2D(20,0));
            addFooter(content, page, PAGE_NUMBER++);
        } catch (Exception e) {
            throw new IOException("Failed to create PDF page", e);
        }

        return page;
    }

    private PDPage createTaskPages(AdvDocTask taskData) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        try (PDPageContentStream content = new PDPageContentStream(document, page)) {
            addBackground(content, page);
            content.beginText();

            List<String> taskLines = CustomPDFMethods.splitTextIntoLines(taskData.getTitle(), boldFont, 18,450);
            content.newLineAtOffset(40, page.getArtBox().getHeight()-60);
            for (String line : taskLines) {
                content.setFont(boldFont, 18);
                content.setNonStrokingColor(Color.decode(userData.getThemeColor()));
                content.showText(line);
                content.newLineAtOffset(0, -20);
            }
            content.newLineAtOffset(0, -30);
            content.setFont(mediumFont, 14);
            content.setNonStrokingColor(Color.BLACK);
            content.showText("Treść zadania:");

            content.setFont(lightFont, 14);
            content.newLineAtOffset(0, -20);

            drawJustifiedText(
                    content,
                    CustomPDFMethods.splitTextIntoLines(taskData.getTask(), mediumFont, 14, 475),
                    lightFont,
                    14,
                    475
            );

            content.newLineAtOffset(0, -30);
            content.setFont(mediumFont, 14);
            content.setNonStrokingColor(Color.BLACK);
            content.showText("Elementy idei stopnia realizowane tym zadaniem:");

            content.setFont(lightFont, 14);
            for (String part : taskData.getIdeaPart().split("\n")) {
                content.newLineAtOffset(0, -5);
                for (String line : CustomPDFMethods.splitTextIntoLines(part, lightFont, 14, 475)) {
                    content.newLineAtOffset(0, -20);
                    content.showText(line);
                }
            }


            content.endText();
            addFooter(content, page, PAGE_NUMBER++);
        }
        return page;
    }

    private void addBackground(PDPageContentStream content, PDPage page) throws IOException {
        drawCenteredImage(
                content,
                page,
                userData.getBackgroundImagePath(),
                page.getBleedBox().getWidth(),
                new Vector2D(0,0)
        );

    }

    private void addFooter(PDPageContentStream content, PDPage page, int pageNumber) throws IOException {
        drawCenteredImage(content, page, "src/main/resources/images/footer.png", page.getArtBox().getWidth()/1.5f, new Vector2D(-20, -380));
        PDImageXObject image = PDImageXObject.createFromFile(userData.getSideImagePath() + (PAGE_NUMBER % 2 == 0 ? "-1.png" : "-0.png"), document);
        Vector2D imageSize = CustomPDFMethods.getImageScaleToSize(image, page.getArtBox().getHeight(), false);

        content.drawImage(image, page.getArtBox().getWidth()-40, 0, imageSize.getX(), imageSize.getY());
        content.beginText();
        content.setFont(boldFont, 18);
        content.setNonStrokingColor(Color.WHITE);
        content.setLeading(14);

        float pageNumberWidth = boldFont.getStringWidth(String.valueOf(pageNumber)) / 1000 * 18;
        content.newLineAtOffset(page.getArtBox().getWidth() - 20 - pageNumberWidth/2, 20);
        content.showText(String.valueOf(pageNumber));
        content.endText();

    }

    private void drawCenteredImage(PDPageContentStream content, PDPage page, String src, float size, Vector2D offset) throws IOException {
        PDImageXObject image = PDImageXObject.createFromFile(src, document);
        Vector2D imageSize = CustomPDFMethods.getImageScaleToSize(image, size, true);
        Vector2D imagePos = CustomPDFMethods.getMiddleWithSize(page, imageSize);

        content.drawImage(image, imagePos.getX() + offset.getX(), imagePos.getY() + offset.getY(), imageSize.getX(), imageSize.getY());
    }

    private void drawContent(PDPageContentStream content, PDPage page, AdvDocData data, Vector2D offset) throws IOException {
        content.beginText();

        float currentY = 550;
        float previousX = offset.getX();

        // Title
        String title = "KARTA PRÓBY NA STOPIEŃ";
        content.setFont(boldFont, 24);
        content.setNonStrokingColor(Color.BLACK);
        float titleX = CustomPDFMethods.getNextLineCenterOffset(title, boldFont, 24, page);
        content.newLineAtOffset(titleX - previousX, currentY-100);
        content.showText(title);
        previousX = titleX;
        currentY -= 25;

        // Advancement name
        content.setFont(boldFont, 24);
        content.setNonStrokingColor(Color.decode(userData.getThemeColor()));
        float advX = CustomPDFMethods.getNextLineCenterOffset(data.getAdvancementName(), boldFont, 24, page);
        content.newLineAtOffset(advX - previousX, currentY - 550);
        content.showText(data.getAdvancementName());
        previousX = advX;
        currentY -= 50;

        // Mentee label
        String menteeLabel = "Imię, nazwisko, stopień podopiecznego";
        content.setFont(mediumFont, 18);
        content.setNonStrokingColor(Color.decode(userData.getThemeColor()));
        float menteeLabelX = CustomPDFMethods.getNextLineCenterOffset(menteeLabel, mediumFont, 18, page);
        content.newLineAtOffset(menteeLabelX - previousX, currentY - (550 - 25));
        content.showText(menteeLabel);
        previousX = menteeLabelX;
        currentY -= 25;

        // Mentee name
        content.setFont(lightFont, 18);
        content.setNonStrokingColor(Color.BLACK);
        float menteeNameX = CustomPDFMethods.getNextLineCenterOffset(data.getMenteeName(), lightFont, 18, page);
        content.newLineAtOffset(menteeNameX - previousX, -25);
        content.showText(data.getMenteeName());
        previousX = menteeNameX;
        currentY -= 50;

        // Mentor label
        String mentorLabel = "Imię, nazwisko, stopień opiekuna";
        content.setFont(mediumFont, 18);
        content.setNonStrokingColor(Color.decode(userData.getThemeColor()));
        float mentorLabelX = CustomPDFMethods.getNextLineCenterOffset(mentorLabel, mediumFont, 18, page);
        content.newLineAtOffset(mentorLabelX - previousX, -25);
        content.showText(mentorLabel);
        previousX = mentorLabelX;
        currentY -= 25;

        // Mentor name
        content.setFont(lightFont, 18);
        content.setNonStrokingColor(Color.BLACK);
        float mentorNameX = CustomPDFMethods.getNextLineCenterOffset(data.getMentorName(), lightFont, 18, page);
        content.newLineAtOffset(mentorNameX - previousX, -25);
        content.showText(data.getMentorName());

        content.endText();
    }

    private String findLongestLine(List<String> lines) {
        return lines.stream()
                .max((line1, line2) -> {
                    try {
                        float width1 = lightFont.getStringWidth(line1) / 1000 * 14;
                        float width2 = lightFont.getStringWidth(line2) / 1000 * 14;
                        return Float.compare(width1, width2);
                    } catch (IOException e) {
                        return 0;
                    }
                })
                .orElse("");
    }

    private void drawJustifiedText(PDPageContentStream content, List<String> lines, PDFont font, float fontSize, float width) throws IOException {
        if (lines.isEmpty()) return;

        String lastLine = lines.get(lines.size() - 1);
        List<String> justifiedLines = lines.subList(0, lines.size() - 1);

        for (String line : justifiedLines) {
            float lineWidth = font.getStringWidth(line) / 1000 * fontSize;
            float spacing = lineWidth < width ? (width - lineWidth) / Math.max(1, line.length() - 1) : 0;

            content.setCharacterSpacing(spacing);
            content.showText(line);
            content.setCharacterSpacing(0);
            content.newLineAtOffset(0, -18);
        }

        content.showText(lastLine);
    }
}