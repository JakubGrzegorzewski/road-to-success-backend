package grzegorzewski.roadtosuccesbackend.Document.Write;

import lombok.Getter;
import lombok.Setter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;

@Getter
@Setter
public class DocumentData {
    private final float textWidth = 450f;
    private int PAGE_NUMBER = 1;

    private PDDocument document;
    private PDFont boldFont;
    private PDFont mediumFont;
    private PDFont lightFont;

    public void incrementPageNumber() {
        PAGE_NUMBER++;
    }
}
