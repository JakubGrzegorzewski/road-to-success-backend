package grzegorzewski.roadtosuccesbackend.Mapper;

import grzegorzewski.roadtosuccesbackend.Dto.BasicRankInProgressDto;
import grzegorzewski.roadtosuccesbackend.Dto.RankInProgressDto;
import grzegorzewski.roadtosuccesbackend.Model.AppUser;
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
                .style(rankInProgress.getStyle())
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
                .style(dto.getStyle())
                .build();
    }

    public List<RankInProgressDto> toDtoList(List<RankInProgress> ranksInProgress) {
        return ranksInProgress.stream().map(this::toDto).collect(Collectors.toList());
    }

    public static BasicRankInProgressDto toBasicDto(RankInProgress rankInProgress) {
        AppUser mentor = rankInProgress.getMentor();
        return BasicRankInProgressDto.builder()
                .rankInProgressId(rankInProgress.getId())
                .rankId(rankInProgress.getRank().getId())
                .fullName(rankInProgress.getRank().getFullName())
                .shortName(rankInProgress.getRank().getShortName())
                .userId(rankInProgress.getUser().getId())
                .userName(rankInProgress.getUser().getFullName())
                .mentorId(mentor != null ? mentor.getId() : null)
                .mentorName(mentor != null ? mentor.getFullName() : null)
                .status(rankInProgress.getStatus())
                .style(rankInProgress.getStyle())
                .build();
    }
}