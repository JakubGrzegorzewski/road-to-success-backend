package grzegorzewski.roadtosuccesbackend.Document.Write;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class providing custom methods for PDF manipulation using Apache PDFBox.
 */
public class PDFLayoutUtils {

    /**
        * Returns the coordinates to center an object of given size on the page.
        * @param page The PDF page.
        * @param size The size of the object to be centered.
        * @return A Vector2D representing the (x, y) coordinates to center the object.
     **/
    public static Vector2D getMiddleWithSize(PDPage page, Vector2D size) {
        PDRectangle mediaBox = page.getMediaBox();
        float x = (mediaBox.getWidth() - size.getX()) / 2;
        float y = (mediaBox.getHeight() - size.getY()) / 2;
        return new Vector2D(x, y);
    }

    /**
        * Calculates the scaled dimensions of an image to fit within a specified size while maintaining its aspect ratio.
        * @param pdImage The PDImageXObject representing the image.
        * @param size The target size (width or height) to scale the image to.
        * @param isWidth A boolean indicating whether the size parameter refers to width (true) or height (false).
        * @return A Vector2D containing the scaled width and height of the image.
     **/
    public static Vector2D getImageScaleToSize(PDImageXObject pdImage, float size, boolean isWidth) {
        float scale = isWidth ? size / pdImage.getWidth() : size / pdImage.getHeight();
        return new Vector2D(pdImage.getWidth() * scale, pdImage.getHeight() * scale);
    }

    /**
        * Calculates the horizontal offset needed to center a line of text on a PDF page.
        * @param text The text string to be centered.
        * @param font The PDFont used to render the text.
        * @param fontSize The font size used for the text.
        * @param page The PDF page where the text will be placed.
        * @return The horizontal offset (in points) needed to center the text on the page.
        * @throws IOException If an error occurs while calculating the string width.
     **/
    public static float getNextLineCenterOffset(String text, PDFont font, float fontSize, PDPage page) throws IOException {
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;
        float pageWidth = page.getMediaBox().getWidth();
        return (pageWidth - textWidth) / 2;
    }

    /**
        * Splits a given text into lines that fit within a specified maximum width, taking into account word boundaries and font metrics.
        * @param text The text to be split into lines.
        * @param font The PDFont used to measure the text width.
        * @param fontSize The font size used for the text.
        * @param maxWidth The maximum width (in points) that each line should not exceed.
        * @return A list of strings, where each string is a line of text that fits within the specified width.
        * @throws IOException If an error occurs while measuring the text width.
     **/
    public static List<String> splitTextIntoLines(String text, PDFont font, float fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        String[] paragraphs = text.split("\\n");

        for (String paragraph : paragraphs) {
            String[] words = paragraph.split(" ");
            StringBuilder currentLine = new StringBuilder();

            for (String word : words) {
                if (font.getStringWidth(word) / 1000 * fontSize > maxWidth) {
                    if (!currentLine.isEmpty()) {
                        lines.add(currentLine.toString());
                        currentLine = new StringBuilder();
                    }
                    lines.add(word);
                    continue;
                }

                String testLine = currentLine.isEmpty() ? word : currentLine + " " + word;
                float testWidth = font.getStringWidth(testLine) / 1000 * fontSize;

                if (testWidth > maxWidth) {
                    String lineToAdd = currentLine.toString();
                    int lastSpaceIndex = lineToAdd.lastIndexOf(' ');

                    if (lastSpaceIndex != -1 && (lineToAdd.length() - lastSpaceIndex - 1) <= 1) {
                        lines.add(lineToAdd.substring(0, lastSpaceIndex));
                        String orphan = lineToAdd.substring(lastSpaceIndex + 1);
                        currentLine = new StringBuilder(orphan).append(" ").append(word);
                    } else {
                        lines.add(lineToAdd);
                        currentLine = new StringBuilder(word);
                    }
                } else {
                    currentLine.append(currentLine.isEmpty() ? "" : " ").append(word);
                }
            }

            if (!currentLine.isEmpty()) {
                lines.add(currentLine.toString());
            }
        }

        return lines;
    }

    /**
        * Calculates the total height of a block of text when rendered with a specific font, font size, line width, and line spacing.
        * @param text The text block to be measured.
        * @param font The PDFont used to render the text.
        * @param fontSize The font size used for the text.
        * @param lineWidth The maximum width (in points) that each line should not exceed.
        * @param lineSpacing The line spacing factor (e.g., 1.0 for single spacing, 1.5 for one-and-a-half spacing).
        * @return The total height (in points) of the text block when rendered.
        * @throws IOException If an error occurs while measuring the text width or font metrics.
     **/
    public static float calculateTextBlockHeight(String text, PDFont font, float fontSize,
                                                 float lineWidth, float lineSpacing) throws IOException {
        if (text == null || text.trim().isEmpty()) {
            return 0f;
        }

        List<String> lines = PDFLayoutUtils.splitTextIntoLines(text, font, fontSize, lineWidth);
        float singleLineHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
        float lineHeightWithSpacing = singleLineHeight * (lines.size() * Math.abs(lineSpacing));

        return lines.size() * lineHeightWithSpacing;
    }


    /**
     * Finds the longest line in a list of strings based on the rendered width using a specified font.
     * @param lines The list of strings to be evaluated.
     * @param lightFont The PDFont used to measure the text width.
     * @return The longest line from the list based on rendered width, or an empty string if the list is empty.
     **/
    public static String findLongestLine(List<String> lines, PDFont lightFont) {
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


    /**
        * Draws an image centered on a PDF page with a specified size and offset.
        * @param content The PDPageContentStream used to draw the image.
        * @param page The PDF page where the image will be drawn.
        * @param src The file path of the image to be drawn.
        * @param size The target size (width or height) to scale the image to.
        * @param offset A Vector2D representing the (x, y) offset to apply to the centered position.
        * @param document The PDDocument containing the page and image resources.
        * @throws IOException If an error occurs while loading or drawing the image.
     **/
    public static void drawCenteredImage(PDPageContentStream content, PDPage page, String src, float size, Vector2D offset, PDDocument document) throws IOException {
        PDImageXObject image = PDImageXObject.createFromFile(src, document);
        Vector2D imageSize = PDFLayoutUtils.getImageScaleToSize(image, size, true);
        Vector2D imagePos = PDFLayoutUtils.getMiddleWithSize(page, imageSize);

        content.drawImage(image, imagePos.getX() + offset.getX(), imagePos.getY() + offset.getY(), imageSize.getX(), imageSize.getY());
    }

    /**
        * Draws a list of text lines on a PDF content stream with justification, ensuring that lines (except the last) are spaced to fill the specified width.
        * @param content The PDPageContentStream used to draw the text.
        * @param lines The list of text lines to be drawn.
        * @param font The PDFont used to render the text.
        * @param fontSize The font size used for the text.
        * @param width The maximum width (in points) that each line should fill
        * @return Text lfet if it won't fit on page.
        * @throws IOException If an error occurs while drawing the text.
     **/
    public static List<String> drawJustifiedText(PDPageContentStream content, List<String> lines, PDFont font, float fontSize, float width) throws IOException {
        if (lines.isEmpty()) return null;

        String lastLine = lines.get(lines.size() - 1);
        List<String> justifiedLines = lines.subList(0, lines.size() - 1);
        StringBuilder taskText = new StringBuilder();
        List<String> textLeftToDrawOnNextPage = new ArrayList<>();


        content.setLeading(1.2f * fontSize);

        for (String line : justifiedLines) {
            taskText.append(line);
            float linesHeight = PDFLayoutUtils.calculateTextBlockHeight(
                    taskText.toString(),
                    font,
                    18,
                    width,
                    -20);

            if (linesHeight > 711200) {
                textLeftToDrawOnNextPage.add(line);
                continue;
            }


            float lineWidth = font.getStringWidth(line) / 1000 * fontSize;
            float spacing = (lineWidth < width && lineWidth >= width * 0.75f) ? (width - lineWidth) / Math.max(1, line.length() - 1) : 0;

            content.setCharacterSpacing(spacing);
            content.showText(line);
            content.setCharacterSpacing(0);
            content.newLine();
        }

        content.showText(lastLine);

        return textLeftToDrawOnNextPage;
    }

    /**
        * Draws a list of text lines on a PDF content stream, handling line breaks and ensuring that each line fits within the specified width.
        * @param content The PDPageContentStream used to draw the text.
        * @param lines The list of text lines to be drawn.
        * @param font The PDFont used to render the text.
        * @param fontSize The font size used for the text.
        * @param width The maximum width (in points) that each line should not exceed.
        * @throws IOException If an error occurs while drawing the text.
     */
    public static void drawMultilineText(PDPageContentStream content, List<String> lines, PDFont font, float fontSize, float width) throws IOException {
        if (lines.isEmpty()) return;
        content.setLeading(1.2f * fontSize);

        content.setFont(font, fontSize);
        for (String part : lines) {
            content.newLineAtOffset(0, -5);
            for (String line : PDFLayoutUtils.splitTextIntoLines(part, font, fontSize, width)) {
                content.showText(line);
                content.newLine();
            }
        }

    }

}
