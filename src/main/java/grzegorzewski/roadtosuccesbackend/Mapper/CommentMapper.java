package grzegorzewski.roadtosuccesbackend.Mapper;

import grzegorzewski.roadtosuccesbackend.Dto.CommentDto;
import grzegorzewski.roadtosuccesbackend.Model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public CommentDto toDto(Comment comment) {
        if (comment == null) return null;

        return CommentDto.builder()
                .id(comment.getId())
                .date(comment.getDate())
                .userId(comment.getUser() != null ? comment.getUser().getId() : null)
                .content(comment.getContent())
                .taskId(comment.getTask() != null ? comment.getTask().getId() : null)
                .build();
    }

    public Comment toEntity(CommentDto dto) {
        if (dto == null) return null;

        return Comment.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .content(dto.getContent())
                .build();
    }

    public List<CommentDto> toDtoList(List<Comment> comments) {
        return comments.stream().map(this::toDto).collect(Collectors.toList());
    }
}
