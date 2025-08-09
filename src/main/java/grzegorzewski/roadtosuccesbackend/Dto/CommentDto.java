package grzegorzewski.roadtosuccesbackend.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime date;
    private Long userId;
    private String content;
    private Long taskId;
}