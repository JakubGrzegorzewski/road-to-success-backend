package grzegorzewski.roadtosuccesbackend.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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
    @JsonBackReference("rankInProgress-rank")
    private Rank rank;

    @ManyToOne
    @JsonBackReference("user-rankInProgress")
    private AppUser user;

    @ManyToOne
    @JsonBackReference("mentor-rankInProgress")
    private AppUser mentor;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "rankInProgress", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("rankInProgress-tasks")
    private List<Task> tasks;
}