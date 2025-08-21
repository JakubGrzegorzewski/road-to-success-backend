package grzegorzewski.roadtosuccesbackend.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime date;

    @ManyToOne
    @JsonBackReference("user-comments")
    private AppUser user;

    @Lob
    private String content;

    @ManyToOne
    @JsonBackReference("task-comments")
    private Task task;
}