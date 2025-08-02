package grzegorzewski.roadtosuccesbackend.Mapper;

import grzegorzewski.roadtosuccesbackend.Dto.RankDto;
import grzegorzewski.roadtosuccesbackend.Model.Rank;
import grzegorzewski.roadtosuccesbackend.Model.RankInProgress;
import grzegorzewski.roadtosuccesbackend.Model.Requirement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RankMapper {

    public RankDto toDto(Rank rank) {
        if (rank == null) return null;

        return RankDto.builder()
                .id(rank.getId())
                .fullName(rank.getFullName())
                .shortName(rank.getShortName())
                .idea(rank.getIdea())
                .startRequirements(rank.getStartRequirements())
                .endRequirements(rank.getEndRequirements())
                .requirementIds(rank.getRequirements() != null ?
                        rank.getRequirements().stream()
                                .map(Requirement::getId)
                                .collect(Collectors.toList()) : null)
                .ranksInProgressIds(rank.getRanksInProgress() != null ?
                        rank.getRanksInProgress().stream()
                                .map(RankInProgress::getId)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public Rank toEntity(RankDto dto) {
        if (dto == null) return null;

        return Rank.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .shortName(dto.getShortName())
                .idea(dto.getIdea())
                .startRequirements(dto.getStartRequirements())
                .endRequirements(dto.getEndRequirements())
                .build();
    }

    public List<RankDto> toDtoList(List<Rank> ranks) {
        return ranks.stream().map(this::toDto).collect(Collectors.toList());
    }
}