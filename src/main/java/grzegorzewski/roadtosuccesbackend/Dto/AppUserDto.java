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
public class AppUserDto {
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String role;
    private List<Long> ranksInProgressIds;
    private List<Long> mentorRanksInProgressIds;
    private List<Long> commentIds;
}
