package grzegorzewski.roadtosuccesbackend.Dto;

import grzegorzewski.roadtosuccesbackend.Model.Status;
import grzegorzewski.roadtosuccesbackend.Model.Style;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicRankInProgressDto {
    private Long rankInProgressId;
    private Long rankId;
    private String fullName;
    private String shortName;
    private Long userId;
    private String userName;
    private Long mentorId;
    private String mentorName;
    private Status status;
    private Style style;
}
