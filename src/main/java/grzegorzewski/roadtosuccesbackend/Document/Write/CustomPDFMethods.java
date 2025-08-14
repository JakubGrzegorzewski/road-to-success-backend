package grzegorzewski.roadtosuccesbackend.Document.Write;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomPDFMethods {

    public static Vector2D getMiddleWithSize(PDPage page, Vector2D size) {
        PDRectangle mediaBox = page.getMediaBox();
        float x = (mediaBox.getWidth() - size.getX()) / 2;
        float y = (mediaBox.getHeight() - size.getY()) / 2;
        return new Vector2D(x, y);
    }

    public static Vector2D getImageScaleToSize(PDImageXObject pdImage, float size, boolean isWidth) {
        float scale = isWidth ? size / pdImage.getWidth() : size / pdImage.getHeight();
        return new Vector2D(pdImage.getWidth() * scale, pdImage.getHeight() * scale);
    }

    public static float getNextLineCenterOffset(String text, PDFont font, float fontSize, PDPage page) throws IOException {
        float textWidth = font.getStringWidth(text) / 1000 * fontSize;
        float pageWidth = page.getMediaBox().getWidth();
        return (pageWidth - textWidth) / 2;
    }

    public static List<String> splitTextIntoLines(String text, PDFont font, float fontSize, float maxWidth) throws IOException {
            List<String> lines = new ArrayList<>();
            String[] words = text.split(" ");
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

            return lines;
        }


}
