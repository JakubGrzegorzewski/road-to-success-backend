package grzegorzewski.roadtosuccesbackend.Mapper;

import grzegorzewski.roadtosuccesbackend.Dto.AppUserDto;
import grzegorzewski.roadtosuccesbackend.Dto.BasicAppUserDto;
import grzegorzewski.roadtosuccesbackend.Dto.BasicRankInProgressDto;
import grzegorzewski.roadtosuccesbackend.Model.AppUser;
import grzegorzewski.roadtosuccesbackend.Model.Comment;
import grzegorzewski.roadtosuccesbackend.Model.RankInProgress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppUserMapper {

    public AppUserDto toDto(AppUser user) {
        if (user == null) return null;

        return AppUserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .ranksInProgressIds(user.getRanksInProgress() != null ?
                        user.getRanksInProgress().stream()
                                .map(RankInProgress::getId)
                                .collect(Collectors.toList()) : null)
                .mentorRanksInProgressIds(user.getMentorRanksInProgress() != null ?
                        user.getMentorRanksInProgress().stream()
                                .map(RankInProgress::getId)
                                .collect(Collectors.toList()) : null)
                .commentIds(user.getComments() != null ?
                        user.getComments().stream()
                                .map(Comment::getId)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public AppUser toEntity(AppUserDto dto) {
        if (dto == null) return null;

        return AppUser.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    public List<AppUserDto> toDtoList(List<AppUser> users) {
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }

    public BasicAppUserDto toBasicDto(AppUser user) {
        if (user == null) return null;
        return BasicAppUserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }

    public List<BasicAppUserDto> toBasicDtoList(List<AppUser> users) {
        return users.stream().map(this::toBasicDto).collect(Collectors.toList());
    }
}