package grzegorzewski.roadtosuccesbackend.Dto;

import grzegorzewski.roadtosuccesbackend.Model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private Long id;
    private Long rankInProgressId;
    private Long requirementId;
    private String content;
    private Status status;
    private String partIdea;
    private List<Long> commentIds;
}
