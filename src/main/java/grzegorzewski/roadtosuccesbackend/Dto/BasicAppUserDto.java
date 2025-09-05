package grzegorzewski.roadtosuccesbackend.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicAppUserDto {
    private Long id;
    private String fullName;
    private String role;
}
