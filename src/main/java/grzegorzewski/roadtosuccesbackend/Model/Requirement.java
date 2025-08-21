package grzegorzewski.roadtosuccesbackend.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Requirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @Lob
    private String content;

    @ManyToOne
    @JsonBackReference("rank-requirements")
    private Rank rank;

    @OneToMany(mappedBy = "requirement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("requirement-tasks")
    private List<Task> tasks;
}