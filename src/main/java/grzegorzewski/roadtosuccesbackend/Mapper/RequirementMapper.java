package grzegorzewski.roadtosuccesbackend.Mapper;

import grzegorzewski.roadtosuccesbackend.Dto.RequirementDto;
import grzegorzewski.roadtosuccesbackend.Model.Requirement;
import grzegorzewski.roadtosuccesbackend.Model.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequirementMapper {

    public RequirementDto toDto(Requirement requirement) {
        if (requirement == null) return null;

        return RequirementDto.builder()
                .id(requirement.getId())
                .number(requirement.getNumber())
                .content(requirement.getContent())
                .rankId(requirement.getRank() != null ? requirement.getRank().getId() : null)
                .taskIds(requirement.getTasks() != null ?
                        requirement.getTasks().stream()
                                .map(Task::getId)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public Requirement toEntity(RequirementDto dto) {
        if (dto == null) return null;

        return Requirement.builder()
                .id(dto.getId())
                .number(dto.getNumber())
                .content(dto.getContent())
                .build();
    }

    public List<RequirementDto> toDtoList(List<Requirement> requirements) {
        return requirements.stream().map(this::toDto).collect(Collectors.toList());
    }
}