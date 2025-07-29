package grzegorzewski.roadtosuccesbackend.Controller;

import grzegorzewski.roadtosuccesbackend.Model.Comment;
import grzegorzewski.roadtosuccesbackend.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/Comment")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<Comment> findById(@PathVariable long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<List<Comment>> findAllCommentsForTask(@PathVariable long taskId) {
        return ResponseEntity.ok(commentService.findAllCommentsForTask(taskId));
    }

    @PostMapping
    public ResponseEntity<Comment> save(@RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.save(comment));
    }

    @PutMapping
    public ResponseEntity<Comment> update(@RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.update(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
