package grzegorzewski.roadtosuccesbackend.Mapper;

import grzegorzewski.roadtosuccesbackend.Dto.TaskDto;
import grzegorzewski.roadtosuccesbackend.Model.Comment;
import grzegorzewski.roadtosuccesbackend.Model.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public TaskDto toDto(Task task) {
        if (task == null) return null;

        return TaskDto.builder()
                .id(task.getId())
                .rankInProgressId(task.getRankInProgress() != null ? task.getRankInProgress().getId() : null)
                .requirementId(task.getRequirement() != null ? task.getRequirement().getId() : null)
                .content(task.getContent())
                .status(task.getStatus())
                .partIdea(task.getPartIdea())
                .commentIds(task.getComments() != null ?
                        task.getComments().stream()
                                .map(Comment::getId)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public Task toEntity(TaskDto dto) {
        if (dto == null) return null;

        return Task.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .status(dto.getStatus())
                .partIdea(dto.getPartIdea())
                .build();
    }

    public List<TaskDto> toDtoList(List<Task> tasks) {
        return tasks.stream().map(this::toDto).collect(Collectors.toList());
    }
}