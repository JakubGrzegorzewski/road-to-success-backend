package grzegorzewski.roadtosuccesbackend.Document.Write;

import lombok.Getter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Getter
public class AdvancementDocument {
    private final UserData userData;
    private final DocumentData documentData;

    public AdvancementDocument(UserData userData) throws IOException {
        documentData = new DocumentData();
        documentData.setDocument(new PDDocument());
        documentData.setBoldFont(
                PDType0Font.load(
                        documentData.getDocument(),
                        new java.io.File("src/main/resources/fonts/Museo-700.ttf"))
        );
        documentData.setMediumFont(
                PDType0Font.load(
                        documentData.getDocument(),
                        new java.io.File("src/main/resources/fonts/Museo-500.ttf"))
        );
        documentData.setLightFont(
                PDType0Font.load(
                        documentData.getDocument(),
                        new java.io.File("src/main/resources/fonts/Museo-300.ttf"))
        );
        this.userData = userData;
    }

    public void generateDocument(ByteArrayOutputStream outputStream) throws IOException {
        PDPage firstPage = createFirstPage(userData);
        documentData.getDocument().addPage(firstPage);

        if (userData.getIdea() != null) {
            createSecondPage();
        }

        for (TaskData task : userData.getTasks()) {
            createTaskPages(task);
        }

        documentData.getDocument().save(outputStream);
        documentData.getDocument().close();
    }

    private PDPage createFirstPage(UserData data) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);

        try (PDPageContentStream content = new PDPageContentStream(documentData.getDocument(), page)) {
            addBackground(content, page);
            PDFLayoutUtils.drawCenteredImage(content, page, data.getImagePath(), 120, new Vector2D(-20, 170), documentData.getDocument());

            float currentY = page.getArtBox().getHeight() - 400;

            // Title
            Paragraph titleParagraph = new Paragraph();
            titleParagraph.setFont(documentData.getBoldFont());
            titleParagraph.setFontSize(24);
            titleParagraph.setLeadingSize(26);
            titleParagraph.setWidth(documentData.getTextWidth());
            titleParagraph.setColor(Color.BLACK);
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            titleParagraph.setText(List.of("KARTA PRÓBY NA STOPIEŃ"));
            titleParagraph.setPosition(new Vector2D(40, currentY));
            titleParagraph.drawParagraph(page, documentData.getDocument(), 80);

            // Advancement name
            Paragraph advancementParagraph = titleParagraph.clone();
            advancementParagraph.setColor(Color.decode(userData.getThemeColor()));
            advancementParagraph.setText(List.of(data.getAdvancementName()));
            advancementParagraph.setPosition(new Vector2D(40, titleParagraph.getParagraphBottomY() - 5));
            advancementParagraph.drawParagraph(page, documentData.getDocument(), 80);

            // Mentee label
            Paragraph menteeLabelParagraph = new Paragraph();
            menteeLabelParagraph.setFont(documentData.getMediumFont());
            menteeLabelParagraph.setFontSize(18);
            menteeLabelParagraph.setLeadingSize(20);
            menteeLabelParagraph.setWidth(documentData.getTextWidth());
            menteeLabelParagraph.setColor(Color.decode(userData.getThemeColor()));
            menteeLabelParagraph.setAlignment(ParagraphAlignment.CENTER);
            menteeLabelParagraph.setText(List.of("Imię, nazwisko, stopień podopiecznego"));
            menteeLabelParagraph.setPosition(new Vector2D(40, advancementParagraph.getParagraphBottomY() - 10));
            menteeLabelParagraph.drawParagraph(page, documentData.getDocument(), 80);

            // Mentee name
            Paragraph menteeNameParagraph = menteeLabelParagraph.clone();
            menteeNameParagraph.setFont(documentData.getLightFont());
            menteeNameParagraph.setColor(Color.BLACK);
            menteeNameParagraph.setText(List.of(data.getMenteeName()));
            menteeNameParagraph.setPosition(new Vector2D(40, menteeLabelParagraph.getParagraphBottomY() - 5));
            menteeNameParagraph.drawParagraph(page, documentData.getDocument(), 80);

            // Mentor label
            Paragraph mentorLabelParagraph = menteeLabelParagraph.clone();
            mentorLabelParagraph.setText(List.of("Imię, nazwisko, stopień opiekuna"));
            mentorLabelParagraph.setPosition(new Vector2D(40, menteeNameParagraph.getParagraphBottomY() - 5));
            mentorLabelParagraph.drawParagraph(page, documentData.getDocument(), 80);

            // Mentor name
            Paragraph mentorNameParagraph = menteeNameParagraph.clone();
            mentorNameParagraph.setText(List.of(data.getMentorName()));
            mentorNameParagraph.setPosition(new Vector2D(40, mentorLabelParagraph.getParagraphBottomY() - 5));
            mentorNameParagraph.drawParagraph(page, documentData.getDocument(), 80);

            addFooter(content, page, documentData.getPAGE_NUMBER());
            documentData.incrementPageNumber();
        } catch (Exception e) {
            throw new IOException("Failed to create PDF page", e);
        }

        return page;
    }

    private void createSecondPage() throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        try (PDPageContentStream content = new PDPageContentStream(documentData.getDocument(), page)) {
            addBackground(content, page);

            // Idea section title
            Paragraph ideaTitleParagraph = new Paragraph();
            ideaTitleParagraph.setFont(documentData.getBoldFont());
            ideaTitleParagraph.setFontSize(24);
            ideaTitleParagraph.setLeadingSize(26);
            ideaTitleParagraph.setWidth(documentData.getTextWidth());
            ideaTitleParagraph.setColor(Color.decode(userData.getThemeColor()));
            ideaTitleParagraph.setAlignment(ParagraphAlignment.CENTER);
            ideaTitleParagraph.setText(List.of("Idea stopnia"));
            ideaTitleParagraph.setPosition(new Vector2D(40, page.getArtBox().getHeight() - 100));
            ideaTitleParagraph.drawParagraph(page, documentData.getDocument(), 80);

            // Idea text
            Paragraph ideaTextParagraph = new Paragraph();
            ideaTextParagraph.setFont(documentData.getLightFont());
            ideaTextParagraph.setFontSize(14);
            ideaTextParagraph.setLeadingSize(16);
            ideaTextParagraph.setWidth(documentData.getTextWidth());
            ideaTextParagraph.setColor(Color.BLACK);
            ideaTextParagraph.setAlignment(ParagraphAlignment.JUSTIFIED);
            ideaTextParagraph.setText(ideaTextParagraph.splitTextIntoLines(userData.getIdea()));
            ideaTextParagraph.setPosition(new Vector2D(40, ideaTitleParagraph.getParagraphBottomY() - 30));

            List<String> ideaLeft = ideaTextParagraph.drawParagraph(page, documentData.getDocument(), 80);
            if (!ideaLeft.isEmpty()) {
                getDocumentData().getDocument().addPage(page);
                ideaTextParagraph.setText(ideaLeft);
                page = createContinuedIdeaPage(ideaTextParagraph);
            }

            addFooter(content, page, documentData.getPAGE_NUMBER());
            documentData.incrementPageNumber();
        } catch (Exception e) {
            throw new IOException("Failed to create PDF page", e);
        }

        getDocumentData().getDocument().addPage(page);
    }

    private PDPage createContinuedIdeaPage(Paragraph paragraph) throws IOException {
        List<String> textLeft = paragraph.getText();
        PDPage page = new PDPage(PDRectangle.A4);
        while (!textLeft.isEmpty()) {
            page = new PDPage(PDRectangle.A4);
            try (PDPageContentStream content = new PDPageContentStream(documentData.getDocument(), page)) {
                addBackground(content, page);
                addFooter(content, page, documentData.getPAGE_NUMBER());
                documentData.incrementPageNumber();
                paragraph.setPosition(new Vector2D(40, page.getArtBox().getHeight() - 60));
                paragraph.setText(textLeft);
                textLeft = paragraph.drawParagraph(page, documentData.getDocument(), 100);
                if (!textLeft.isEmpty())
                    getDocumentData().getDocument().addPage(page);
            } catch (Exception e) {
                throw new IOException("Failed to create PDF page", e);
            }
        }
        return page;
    }

    private void createTaskPages(TaskData taskData) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);

        try (PDPageContentStream content = new PDPageContentStream(documentData.getDocument(), page)) {
            addBackground(content, page);
            addFooter(content, page, documentData.getPAGE_NUMBER());
            documentData.incrementPageNumber();

            Paragraph taskTitle = new Paragraph();
            taskTitle.setFont(documentData.getBoldFont());
            taskTitle.setFontSize(14);
            taskTitle.setLeadingSize(16);
            taskTitle.setWidth(documentData.getTextWidth());
            taskTitle.setColor(Color.decode(userData.getThemeColor()));
            taskTitle.setAlignment(ParagraphAlignment.LEFT);
            taskTitle.setText(taskTitle.splitTextIntoLines(taskData.getTitle()));
            taskTitle.setPosition(new Vector2D(40, page.getArtBox().getHeight() - 60));
            taskTitle.drawParagraph(page, documentData.getDocument(), 80);

            Paragraph contentHeader = taskTitle.clone();
            contentHeader.setFont(documentData.getMediumFont());
            contentHeader.setColor(Color.BLACK);
            contentHeader.setText(List.of("Treść zadania:"));
            contentHeader.setPosition(new Vector2D(40, taskTitle.getParagraphBottomY() - 20));
            contentHeader.drawParagraph(page, documentData.getDocument(), 80);

            Paragraph contentParagraph = contentHeader.clone();
            contentParagraph.setFont(documentData.getLightFont());
            contentParagraph.setText(
                    contentParagraph.splitTextIntoLines(taskData.getTask())
            );
            contentParagraph.setPosition(new Vector2D(40, contentHeader.getParagraphBottomY() - 10));
            contentParagraph.setAlignment(ParagraphAlignment.JUSTIFIED);
            contentParagraph.setFont(documentData.getLightFont());
            List<String> contentLeft = contentParagraph.drawParagraph(page, documentData.getDocument(), 80);
            if (!contentLeft.isEmpty()) {
                getDocumentData().getDocument().addPage(page);
                contentParagraph.setText(contentLeft);
                page = createContinuedTaskPage(contentParagraph);
            }

            Paragraph ideaHeader = contentHeader.clone();
            ideaHeader.setText(List.of("Idea zadania:"));
            ideaHeader.setPosition(new Vector2D(40, contentParagraph.getParagraphBottomY() - 20));
            ideaHeader.drawParagraph(page, documentData.getDocument(), 80);

            Paragraph ideaContent = contentParagraph.clone();
            ideaContent.setText(
                    ideaContent.splitTextIntoLines(taskData.getIdeaPart())
            );
            ideaContent.setAlignment(ParagraphAlignment.LEFT);
            ideaContent.setPosition(new Vector2D(40, ideaHeader.getParagraphBottomY() - 10));
            List<String> ideaLeft = ideaContent.drawParagraph(page, documentData.getDocument(), 80);
            if (!ideaLeft.isEmpty()) {
                getDocumentData().getDocument().addPage(page);
                ideaContent.setText(ideaLeft);
                page = createContinuedTaskPage(ideaContent);
            }
        }
        catch (Exception e) {
            throw new IOException("Failed to create PDF page", e);
        }
        getDocumentData().getDocument().addPage(page);
    }

    private PDPage createContinuedTaskPage(Paragraph paragraph) throws IOException {
        List<String> textLeft = paragraph.getText();
        PDPage page = new PDPage(PDRectangle.A4);
        while (!textLeft.isEmpty()) {
            page = new PDPage(PDRectangle.A4);
            try (PDPageContentStream content = new PDPageContentStream(documentData.getDocument(), page)) {
                addBackground(content, page);
                addFooter(content, page, documentData.getPAGE_NUMBER());
                documentData.incrementPageNumber();
                paragraph.setPosition(new Vector2D(40, page.getArtBox().getHeight() - 60));
                paragraph.setText(textLeft);
                textLeft = paragraph.drawParagraph(page, documentData.getDocument(), 100);
                if (!textLeft.isEmpty())
                    getDocumentData().getDocument().addPage(page);
            } catch (Exception e) {
                throw new IOException("Failed to create PDF page", e);
            }
        }
        return page;
    }

    private void addBackground(PDPageContentStream content, PDPage page) throws IOException {
        PDFLayoutUtils.drawCenteredImage(
                content,
                page,
                userData.getBackgroundImagePath(),
                page.getBleedBox().getWidth(),
                new Vector2D(0,0),
                documentData.getDocument()
        );
    }

    private void addFooter(PDPageContentStream content, PDPage page, int pageNumber) throws IOException {
        PDFLayoutUtils.drawCenteredImage(content, page, "src/main/resources/images/footer.png", page.getArtBox().getWidth()/1.5f, new Vector2D(-20, -380), documentData.getDocument());
        PDImageXObject image = PDImageXObject.createFromFile(userData.getSideImagePath() + (documentData.getPAGE_NUMBER() % 2 == 0 ? "-1.png" : "-0.png"), documentData.getDocument());
        Vector2D imageSize = PDFLayoutUtils.getImageScaleToSize(image, page.getArtBox().getHeight(), false);

        content.drawImage(image, page.getArtBox().getWidth()-40, 0, imageSize.getX(), imageSize.getY());
        content.beginText();
        content.setFont(documentData.getBoldFont(), 18);
        content.setNonStrokingColor(Color.WHITE);
        content.setLeading(14);

        float pageNumberWidth = documentData.getBoldFont().getStringWidth(String.valueOf(pageNumber)) / 1000 * 18;
        content.newLineAtOffset(page.getArtBox().getWidth() - 20 - pageNumberWidth/2, 20);
        content.showText(String.valueOf(pageNumber));
        content.endText();
    }
}