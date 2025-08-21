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
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String shortName;
    @Lob
    private String idea;
    private List<String> startRequirements;
    private List<String> endRequirements;

    @OneToMany(mappedBy = "rank", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("rank-requirements")
    private List<Requirement> requirements;

    @OneToMany(mappedBy = "rank", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("rankInProgress-rank")
    private List<RankInProgress> ranksInProgress;
}