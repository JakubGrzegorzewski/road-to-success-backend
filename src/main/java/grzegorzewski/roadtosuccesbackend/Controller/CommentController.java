package grzegorzewski.roadtosuccesbackend.Controller;

import grzegorzewski.roadtosuccesbackend.Dto.CommentDto;
import grzegorzewski.roadtosuccesbackend.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/comment")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> findById(@PathVariable long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<List<CommentDto>> findAllCommentsForTask(@PathVariable long taskId) {
        return ResponseEntity.ok(commentService.findAllCommentsForTask(taskId));
    }

    @PostMapping
    public ResponseEntity<CommentDto> save(@RequestBody CommentDto comment) {
        return ResponseEntity.ok(commentService.save(comment));
    }

    @PutMapping
    public ResponseEntity<CommentDto> update(@RequestBody CommentDto comment) {
        return ResponseEntity.ok(commentService.update(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}