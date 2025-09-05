package grzegorzewski.roadtosuccesbackend.Dto;

import grzegorzewski.roadtosuccesbackend.Model.Status;
import grzegorzewski.roadtosuccesbackend.Model.Style;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RankInProgressDto {
    private Long id;
    private Long rankId;
    private Long userId;
    private Long mentorId;
    private Status status;
    private Style style;
    private List<Long> taskIds;
}