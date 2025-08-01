package grzegorzewski.roadtosuccesbackend.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class RankInProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Rank rank;
    @ManyToOne
    @JsonBackReference
    private AppUser user;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Task> tasks;
}
