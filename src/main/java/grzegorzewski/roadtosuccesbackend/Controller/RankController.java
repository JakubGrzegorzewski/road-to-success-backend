package grzegorzewski.roadtosuccesbackend.Controller;

import grzegorzewski.roadtosuccesbackend.Dto.RankDto;
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
    public ResponseEntity<List<RankDto>> getRanks() {
        return ResponseEntity.ok(rankService.getRanks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RankDto> getById(@PathVariable long id) {
        return ResponseEntity.ok(rankService.getById(id));
    }

    @GetMapping("/rankInProgress/{rankInProgressId}")
    public ResponseEntity<RankDto> findRankForRankInProgress(@PathVariable long rankInProgressId) {
        return ResponseEntity.ok(rankService.findRankForRankInProgress(rankInProgressId));
    }

    @PostMapping
    public ResponseEntity<RankDto> save(@RequestBody RankDto rank) {
        return ResponseEntity.ok(rankService.save(rank));
    }

    @PutMapping
    public ResponseEntity<RankDto> update(@RequestBody RankDto rank) {
        return ResponseEntity.ok(rankService.update(rank));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        rankService.delete(id);
        return ResponseEntity.noContent().build();
    }
}