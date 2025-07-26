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
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fullName;
    private String shortName;
    private String idea;
    private List<String> startRequirements;
    private List<String> endRequirements;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Requirements> requirements;
}
