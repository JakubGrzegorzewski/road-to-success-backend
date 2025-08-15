package grzegorzewski.roadtosuccesbackend.Mapper;

import grzegorzewski.roadtosuccesbackend.Dto.RankInProgressDto;
import grzegorzewski.roadtosuccesbackend.Model.RankInProgress;
import grzegorzewski.roadtosuccesbackend.Model.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RankInProgressMapper {

    public RankInProgressDto toDto(RankInProgress rankInProgress) {
        if (rankInProgress == null) return null;

        return RankInProgressDto.builder()
                .id(rankInProgress.getId())
                .rankId(rankInProgress.getRank() != null ? rankInProgress.getRank().getId() : null)
                .userId(rankInProgress.getUser() != null ? rankInProgress.getUser().getId() : null)
                .mentorId(rankInProgress.getMentor() != null ? rankInProgress.getMentor().getId() : null)
                .status(rankInProgress.getStatus())
                .taskIds(rankInProgress.getTasks() != null ?
                        rankInProgress.getTasks().stream()
                                .map(Task::getId)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public RankInProgress toEntity(RankInProgressDto dto) {
        if (dto == null) return null;

        return RankInProgress.builder()
                .id(dto.getId())
                .status(dto.getStatus())
                .build();
    }

    public List<RankInProgressDto> toDtoList(List<RankInProgress> ranksInProgress) {
        return ranksInProgress.stream().map(this::toDto).collect(Collectors.toList());
    }
}