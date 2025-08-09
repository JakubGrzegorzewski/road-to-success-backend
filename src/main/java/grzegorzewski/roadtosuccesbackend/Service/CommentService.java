package grzegorzewski.roadtosuccesbackend.Service;

import grzegorzewski.roadtosuccesbackend.Dto.CommentDto;
import grzegorzewski.roadtosuccesbackend.Mapper.CommentMapper;
import grzegorzewski.roadtosuccesbackend.Model.AppUser;
import grzegorzewski.roadtosuccesbackend.Model.Comment;
import grzegorzewski.roadtosuccesbackend.Model.Task;
import grzegorzewski.roadtosuccesbackend.Repository.CommentRepository;
import grzegorzewski.roadtosuccesbackend.Repository.TaskRepository;
import grzegorzewski.roadtosuccesbackend.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentMapper commentMapper;

    public CommentDto findById(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id " + id));
        return commentMapper.toDto(comment);
    }

    public List<CommentDto> findAllCommentsForTask(long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("Task not found with id " + taskId)
        );
        return commentMapper.toDtoList(task.getComments());
    }

    public CommentDto save(CommentDto commentDto) {
        if (commentDto.getId() != null && commentRepository.existsById(commentDto.getId())) {
            throw new IllegalArgumentException("Comment already exists with id " + commentDto.getId());
        }

        Comment comment = commentMapper.toEntity(commentDto);

        comment.setDate(LocalDateTime.now());

        // Set relationships
        if (commentDto.getUserId() != null) {
            AppUser user = userRepository.findById(commentDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id " + commentDto.getUserId()));
            comment.setUser(user);
        }

        if (commentDto.getTaskId() != null) {
            Task task = taskRepository.findById(commentDto.getTaskId())
                    .orElseThrow(() -> new EntityNotFoundException("Task not found with id " + commentDto.getTaskId()));
            comment.setTask(task);
        }

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }

    public CommentDto update(CommentDto commentDto) {
        if (commentDto.getId() == null) {
            throw new IllegalArgumentException("Comment ID cannot be null for update operation");
        }

        Comment existingComment = commentRepository.findById(commentDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id " + commentDto.getId()));

        existingComment.setContent(commentDto.getContent());
        existingComment.setDate(LocalDateTime.now());

        Comment updatedComment = commentRepository.save(existingComment);
        return commentMapper.toDto(updatedComment);
    }

    public void delete(long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Comment not found with id " + id);
        }
    }
}