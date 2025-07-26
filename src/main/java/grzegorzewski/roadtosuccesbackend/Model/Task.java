package grzegorzewski.roadtosuccesbackend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private RankInProgress rankInProgress;
    private String content;
    private Status status;
    private String partIdea;
    @OneToOne(cascade = CascadeType.ALL)
    private Comment comment;
}
