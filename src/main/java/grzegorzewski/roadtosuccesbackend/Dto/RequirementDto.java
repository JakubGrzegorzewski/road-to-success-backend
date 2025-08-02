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
public class RequirementDto {
    private Long id;
    private float number;
    private String content;
    private Long rankId;
    private List<Long> taskIds;
}