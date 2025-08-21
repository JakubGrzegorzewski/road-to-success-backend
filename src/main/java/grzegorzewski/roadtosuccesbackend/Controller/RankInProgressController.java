package grzegorzewski.roadtosuccesbackend.Controller;

import grzegorzewski.roadtosuccesbackend.Dto.BasicRankInProgressDto;
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

    @GetMapping("/{id}/basic")
    public ResponseEntity<BasicRankInProgressDto> findBasicById(@PathVariable long id) {
        return ResponseEntity.ok(rankInProgressService.findBasicById(id));
    }

    @GetMapping("/{id}/generatePDF")
    public ResponseEntity<byte[]> generateFile(@PathVariable long id) {
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=rank_in_progress.pdf")
                .body(rankInProgressService.generateFile(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RankInProgressDto>> findAllRanksInProgressForUser(@PathVariable long userId) {
        return ResponseEntity.ok(rankInProgressService.findAllRanksInProgressForUser(userId));
    }

    @GetMapping("/user/{userId}/basic")
    public ResponseEntity<List<BasicRankInProgressDto>> findAllBasicRanksInProgressForUser(@PathVariable long userId) {
        return ResponseEntity.ok(rankInProgressService.findAllBasicRanksInProgressForUser(userId));
    }

    @GetMapping("/mentor/{userId}")
    public ResponseEntity<List<RankInProgressDto>> findAllRanksInProgressForMentor(@PathVariable long userId) {
        return ResponseEntity.ok(rankInProgressService.findAllRanksInProgressForMentor(userId));
    }

    @GetMapping("/mentor/{userId}/basic")
    public ResponseEntity<List<BasicRankInProgressDto>> findAllBasicRanksInProgressForMentor(@PathVariable long userId) {
        return ResponseEntity.ok(rankInProgressService.findAllBasicRanksInProgressForMentor(userId));
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