package grzegorzewski.roadtosuccesbackend.Document.Write;


import lombok.Getter;
import lombok.Setter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum ParagraphAlignment {
    LEFT,
    CENTER,
    JUSTIFIED
}


@Getter
@Setter
public class Paragraph implements Cloneable {
    private List<String> text;
    private float width;
    private float fontSize;
    private float leadingSize;
    private PDFont font;
    private Color color;
    private ParagraphAlignment alignment;
    private Vector2D position;

    /**
     * Draws the paragraph on the given page at the specified starting coordinates.
     * @param page the PDPage to draw on
     * @param document the PDDocument containing the page
     * @return List of lines that weren't drawn due to space constraints.
     */
    public List<String> drawParagraph(PDPage page, PDDocument document, float bottomMargin) {
        List<String> result = new ArrayList<>();
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.setNonStrokingColor(color);
            contentStream.setLeading(leadingSize);
            contentStream.newLineAtOffset(position.getX(), position.getY());
            switch (alignment) {
                case LEFT ->
                    result =  drawLeftAlignedLines(contentStream, bottomMargin);

                case CENTER ->
                    result = drawCenteredLines(contentStream, bottomMargin);

                case JUSTIFIED ->
                    result = drawJustifiedLines(contentStream, bottomMargin);
                default -> throw new IllegalArgumentException("Unknown alignment: " + alignment);
            }
            contentStream.endText();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return result;
    }

    private List<String> drawJustifiedLines(PDPageContentStream contentStream, float bottomMargin) throws IOException {
        List<String> linesNotDrawn = new ArrayList<>();
        List<String> linesDrawn = new ArrayList<>();
        List<String> linesToAlign = text.subList(0, text.size() - 1);

        for (String line : linesToAlign) {
            if (!canFitLine(bottomMargin, linesDrawn)) {
                linesNotDrawn.add(line);
                continue;
            }
            float lineWidth = font.getStringWidth(line) / 1000 * fontSize;
            float spacing = (lineWidth < width && lineWidth >= width * 0.75f) ? (width - lineWidth) / Math.max(1, line.length() - 1) : 0;
            if (!line.startsWith("    •"))
                contentStream.setCharacterSpacing(spacing);
            else
                contentStream.setCharacterSpacing(0);
            contentStream.showText(line);
            contentStream.setCharacterSpacing(0);
            contentStream.newLine();
            linesDrawn.add(line);
        }

        if (linesNotDrawn.isEmpty())
            contentStream.showText(text.get(text.size() - 1));

        return linesNotDrawn;
    }

    private List<String> drawLeftAlignedLines(PDPageContentStream contentStream,  float bottomMargin) throws IOException {
        List<String> linesNotDrawn = new ArrayList<>();
        List<String> linesDrawn = new ArrayList<>();
        List<String> linesToAlign = text.subList(0, text.size() - 1);

        for (String line : linesToAlign) {
            if (!canFitLine(bottomMargin, linesDrawn)) {
                linesNotDrawn.add(line);
                continue;
            }
            contentStream.showText(line);
            contentStream.newLine();
            linesDrawn.add(line);
        }

        if (linesNotDrawn.isEmpty())
            contentStream.showText(text.get(text.size() - 1));

        return linesNotDrawn;
    }

    private List<String> drawCenteredLines(PDPageContentStream contentStream, float bottomMargin) throws IOException {
        List<String> linesNotDrawn = new ArrayList<>();
        List<String> linesDrawn = new ArrayList<>();

        for (String line : text) {
            if (!canFitLine(bottomMargin, linesDrawn)) {
                linesNotDrawn.addAll(text.subList(text.indexOf(line), text.size()));
                break;
            }

            float lineWidth = font.getStringWidth(line) / 1000 * fontSize;
            float centerOffset = (width - lineWidth) / 2;

            contentStream.newLineAtOffset(centerOffset, 0);
            contentStream.showText(line);

            contentStream.newLineAtOffset(-centerOffset, 0);

            if (text.indexOf(line) < text.size() - 1 && canFitLine(bottomMargin, linesDrawn)) {
                contentStream.newLine();
            }

            linesDrawn.add(line);
        }

        return linesNotDrawn;
    }

    /**
     * Checks if there is enough space to fit another line of text on the page.
     * @param bottomMargin the bottom margin of the page
     * @param linesDrawn the lines that have already been drawn
     * @return true if there is enough space, false otherwise
     */
    private boolean canFitLine(float bottomMargin, List<String> linesDrawn) {
        float lineHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
        float textHeight = lineHeight * linesDrawn.size();

        return position.getY() - textHeight > bottomMargin;
    }

    /**
     * Gets bottom coordinate of the paragraph after drawing.
     * @return the bottom y-coordinate of the paragraph
     */
    public float getParagraphBottomY() {
        float lineHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
        return position.getY() - (lineHeight * text.size());
    }

    /**
     * Splits a long text into multiple lines based on the specified width.
     * @param text the text to be split
     */
    public List<String> splitTextIntoLines(String text) throws IOException {
        List<String> lines = new ArrayList<>();
        String[] paragraphs = text.split("<br/>");
        for (String paragraph : paragraphs) {
            String[] words = paragraph.split(" ");
            StringBuilder currentLine = new StringBuilder();
            boolean isBulletPoint = false;
            if (paragraph.startsWith("*")) {
                words[0] = "    •";
                isBulletPoint = true;
            }
            for (String word : words) {
                if (font.getStringWidth(word) / 1000 * fontSize > this.width) {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder();
                    lines.add(word);
                    continue;
                }

                String testLine = currentLine.isEmpty() ? word : currentLine + " " + word;
                float testWidth = font.getStringWidth(testLine) / 1000 * fontSize;

                if (testWidth > this.width) {
                    String lineToAdd = currentLine.toString();
                    int lastSpaceIndex = lineToAdd.lastIndexOf(' ');

                    if (lastSpaceIndex != -1 && (lineToAdd.length() - lastSpaceIndex - 1) <= 1) {
                        lines.add(lineToAdd.substring(0, lastSpaceIndex));
                        String orphan = lineToAdd.substring(lastSpaceIndex + 1);
                        currentLine = new StringBuilder(isBulletPoint ? "       " + orphan : orphan).append(" ").append(word);
                    } else {
                        lines.add(lineToAdd);
                        currentLine = new StringBuilder(isBulletPoint ? "       " + word : word);
                    }
                } else {
                    currentLine.append(currentLine.isEmpty() ? "" : " ").append(word);
                }
            }

            lines.add(currentLine.toString());

        }

        return lines;
    }


    @Override
    public Paragraph clone() {
        try {
            Paragraph cloned = (Paragraph) super.clone();
            if (this.text != null) {
                cloned.text = new ArrayList<>(this.text);
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed", e);
        }
    }

}
