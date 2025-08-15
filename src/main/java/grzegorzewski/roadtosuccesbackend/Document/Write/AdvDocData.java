package grzegorzewski.roadtosuccesbackend.Document.Write;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdvDocData {
    String menteeName;
    String mentorName;
    String idea;
    String advancement;
    List<AdvDocTask> tasks;

    public String getAdvancementName() {
        return switch (advancement) {
            case "pwd" -> "PRZEWODNIKA";
            case "phm" -> "PODHARCMISTRZA";
            case "hm" -> "HARCMISTRZA";
            case "ho" -> "HARCERZA ORLEGO";
            case "hr" -> "HARCERZA RZECZYPOSPOLITEJ";
            default -> "Nieznany stopień";
        };
    }

    public String getThemeColor() {
        return switch (advancement) {
            case "pwd" -> "#1E2F5C";
            case "phm" -> "#467A78";
            case "hm" -> "#db0000";
            case "ho", "hr" -> "#9C1006";
            default -> "#FFFFFF";
        };
    }

    public String getImagePath() {
        return switch (advancement) {
            case "pwd" -> "src/main/resources/images/pwd.png";
            case "phm" -> "src/main/resources/images/phm.png";
            case "hm" -> "src/main/resources/images/hm.png";
            case "ho" -> "src/main/resources/images/ho.png";
            case "hr" -> "src/main/resources/images/hr.png";
            default -> "src/main/resources/images/pwd.png";
        };
    }

    public String getSideImagePath() {
        return switch (advancement) {
            case "pwd" -> "src/main/resources/images/side-pwd";
            case "phm" -> "src/main/resources/images/side-phm";
            case "hm" -> "src/main/resources/images/side-hm";
            case "ho", "hr" -> "src/main/resources/images/side-woderer";
            default -> "src/main/resources/images/side-pwd";
        };
    }

    public String getBackgroundImagePath() {
        return switch (advancement) {
            case "pwd", "phm", "hm" -> "src/main/resources/images/instructor-background.png";
            case "ho", "hr" -> "src/main/resources/images/wonderer-background.png";
            default -> "src/main/resources/images/background-pwd.png";
        };
    }



}
