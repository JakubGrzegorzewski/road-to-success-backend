package grzegorzewski.roadtosuccesbackend.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Long id;
    @ManyToOne
    private RankInProgress rankInProgress;
    private String content;
    private Status status;
    private String partIdea;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private List<Comment> comments;
}
