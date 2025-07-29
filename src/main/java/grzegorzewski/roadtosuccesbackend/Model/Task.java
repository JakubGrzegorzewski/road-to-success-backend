package grzegorzewski.roadtosuccesbackend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private RankInProgress rankInProgress;
    private String content;
    private Status status;
    private String partIdea;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;
}
