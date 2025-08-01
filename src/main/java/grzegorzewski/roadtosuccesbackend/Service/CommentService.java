package grzegorzewski.roadtosuccesbackend.Service;

import grzegorzewski.roadtosuccesbackend.Model.Comment;
import grzegorzewski.roadtosuccesbackend.Model.Task;
import grzegorzewski.roadtosuccesbackend.Repository.CommentRepository;
import grzegorzewski.roadtosuccesbackend.Repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Comment findById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Comment not found with id " + id )
                );
    }

    public List<Comment> findAllCommentsForTask(long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("Task not found with id " + taskId)
        );

        return task.getComments();
    }

    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            return commentRepository.save(comment);
        } else {
            if (commentRepository.existsById(comment.getId())) {
                throw new IllegalArgumentException("Comment already exists with id " + comment.getId());
            } else {
                return commentRepository.save(comment);
            }
        }
    }

    public Comment update(Comment comment) {
        if (comment.getId() == null) {
            throw new IllegalArgumentException("Comment ID cannot be null for update operation");
        }

        Comment commentToUpdate = commentRepository.findById(comment.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Comment not found with id " + comment.getId())
                );
        commentToUpdate.setContent(comment.getContent());
        commentToUpdate.setDate(LocalDate.now());
        return commentRepository.save(commentToUpdate);
    }

    public void delete(long id) {
        if (commentRepository.existsById(id))
            commentRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Comment not found with id " + id);
    }
}