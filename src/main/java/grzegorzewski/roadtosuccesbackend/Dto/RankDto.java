package grzegorzewski.roadtosuccesbackend.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RankDto {
    private Long id;
    private String fullName;
    private String shortName;
    private String idea;
    private List<String> startRequirements;
    private List<String> endRequirements;
    private List<Long> requirementIds;
    private List<Long> ranksInProgressIds;
}
