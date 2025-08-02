package grzegorzewski.roadtosuccesbackend.Controller;

import grzegorzewski.roadtosuccesbackend.Dto.RankInProgressDto;
import grzegorzewski.roadtosuccesbackend.Service.RankInProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/rankInProgress")
@RestController
class RankInProgressController {

    @Autowired
    private RankInProgressService rankInProgressService;

    @GetMapping("/{id}")
    public ResponseEntity<RankInProgressDto> findById(@PathVariable long id) {
        return ResponseEntity.ok(rankInProgressService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RankInProgressDto>> findAllRanksInProgressForUser(@PathVariable long userId) {
        return ResponseEntity.ok(rankInProgressService.findAllRanksInProgressForUser(userId));
    }

    @PostMapping
    public ResponseEntity<RankInProgressDto> save(@RequestBody RankInProgressDto rankInProgress) {
        return ResponseEntity.ok(rankInProgressService.save(rankInProgress));
    }

    @PutMapping
    public ResponseEntity<RankInProgressDto> update(@RequestBody RankInProgressDto rankInProgress) {
        return ResponseEntity.ok(rankInProgressService.update(rankInProgress));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        rankInProgressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}