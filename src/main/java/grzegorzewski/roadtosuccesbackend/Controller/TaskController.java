package grzegorzewski.roadtosuccesbackend.Controller;

import grzegorzewski.roadtosuccesbackend.Dto.TaskDto;
import grzegorzewski.roadtosuccesbackend.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/task")
@RestController
class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @GetMapping("/rankInProgress/{rankInProgressId}")
    public ResponseEntity<List<TaskDto>> findAllTasksForRankInProgress(@PathVariable long rankInProgressId) {
        return ResponseEntity.ok(taskService.findAllTasksForRankInProgress(rankInProgressId));
    }

    @PostMapping
    public ResponseEntity<TaskDto> save(@RequestBody TaskDto task) {
        return ResponseEntity.ok(taskService.save(task));
    }

    @PutMapping
    public ResponseEntity<TaskDto> update(@RequestBody TaskDto task) {
        return ResponseEntity.ok(taskService.update(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}