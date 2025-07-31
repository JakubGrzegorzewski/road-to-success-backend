package grzegorzewski.roadtosuccesbackend.Controller;

import grzegorzewski.roadtosuccesbackend.Model.Rank;
import grzegorzewski.roadtosuccesbackend.Service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/rank")
@RestController
public class RankController {
    @Autowired
    private RankService rankService;

    @GetMapping
    public ResponseEntity<List<Rank>> getRanks() {
        return ResponseEntity.ok(rankService.getRanks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rank> getById(@PathVariable long id) {
        return ResponseEntity.ok(rankService.getById(id));
    }

    @GetMapping("/rankInProgress/{rankInProgressId}")
    public ResponseEntity<Rank> findRankForRankInProgress(@PathVariable long rankInProgressId) {
        return ResponseEntity.ok(rankService.findRankForRankInProgress(rankInProgressId));
    }

    @PostMapping
    public ResponseEntity<Rank> save(@RequestBody Rank rank) {
        System.out.println(rank);
        return ResponseEntity.ok(rankService.save(rank));
    }

    @PutMapping
    public ResponseEntity<Rank> update(@RequestBody Rank rank) {
        return ResponseEntity.ok(rankService.update(rank));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        rankService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
