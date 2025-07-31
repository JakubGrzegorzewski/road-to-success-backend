package grzegorzewski.roadtosuccesbackend.Controller;

import grzegorzewski.roadtosuccesbackend.Model.Rank;
import grzegorzewski.roadtosuccesbackend.Model.RankInProgress;
import grzegorzewski.roadtosuccesbackend.Service.RankInProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequestMapping("/api/rankInProgress")
@RestController
class RankInProgressController {

    @Autowired
    private RankInProgressService  rankInProgressService;

    @GetMapping("/{id}")
    public ResponseEntity<RankInProgress> findById(@PathVariable long id) {
        return ResponseEntity.ok(rankInProgressService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RankInProgress>> findAllRanksInProgressForUser(@PathVariable long userId){
        return ResponseEntity.ok(rankInProgressService.findAllRanksInProgressForUser(userId));
    }

    @PostMapping
    public ResponseEntity<RankInProgress> save(@RequestBody RankInProgress rankInProgress) {
        return ResponseEntity.ok(rankInProgressService.save(rankInProgress));
    }
    @PutMapping
    public ResponseEntity<RankInProgress> update(@RequestBody RankInProgress rankInProgress) {
        return ResponseEntity.ok(rankInProgressService.update(rankInProgress));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        rankInProgressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
