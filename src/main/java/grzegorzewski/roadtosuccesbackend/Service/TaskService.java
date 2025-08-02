package grzegorzewski.roadtosuccesbackend.Service;

import grzegorzewski.roadtosuccesbackend.Dto.TaskDto;
import grzegorzewski.roadtosuccesbackend.Mapper.TaskMapper;
import grzegorzewski.roadtosuccesbackend.Model.RankInProgress;
import grzegorzewski.roadtosuccesbackend.Model.Requirement;
import grzegorzewski.roadtosuccesbackend.Model.Task;
import grzegorzewski.roadtosuccesbackend.Repository.RankInProgressRepository;
import grzegorzewski.roadtosuccesbackend.Repository.RequirementRepository;
import grzegorzewski.roadtosuccesbackend.Repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RankInProgressRepository rankInProgressRepository;

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private TaskMapper taskMapper;

    public TaskDto findById(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id " + id));
        return taskMapper.toDto(task);
    }

    public List<TaskDto> findAllTasksForRankInProgress(long rankInProgressId) {
        RankInProgress rankInProgress = rankInProgressRepository.findById(rankInProgressId)
                .orElseThrow(() -> new EntityNotFoundException("RankInProgress not found with id " + rankInProgressId));
        return taskMapper.toDtoList(rankInProgress.getTasks());
    }

    public TaskDto save(TaskDto taskDto) {
        if (taskDto.getId() != null && taskRepository.existsById(taskDto.getId())) {
            throw new IllegalArgumentException("Task already exists with id " + taskDto.getId());
        }

        Task task = taskMapper.toEntity(taskDto);

        // Set relationships
        if (taskDto.getRankInProgressId() != null) {
            RankInProgress rankInProgress = rankInProgressRepository.findById(taskDto.getRankInProgressId())
                    .orElseThrow(() -> new EntityNotFoundException("RankInProgress not found with id " + taskDto.getRankInProgressId()));
            task.setRankInProgress(rankInProgress);
        }

        if (taskDto.getRequirementId() != null) {
            Requirement requirement = requirementRepository.findById(taskDto.getRequirementId())
                    .orElseThrow(() -> new EntityNotFoundException("Requirement not found with id " + taskDto.getRequirementId()));
            task.setRequirement(requirement);
        }

        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    public TaskDto update(TaskDto taskDto) {
        if (taskDto.getId() == null) {
            throw new IllegalArgumentException("Task ID cannot be null for update operation");
        }
        if (!taskRepository.existsById(taskDto.getId())) {
            throw new EntityNotFoundException("Task not found with id " + taskDto.getId());
        }

        Task existingTask = taskRepository.findById(taskDto.getId()).get();
        existingTask.setContent(taskDto.getContent());
        existingTask.setStatus(taskDto.getStatus());
        existingTask.setPartIdea(taskDto.getPartIdea());

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toDto(updatedTask);
    }

    public void delete(long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Task not found with id " + id);
        }
    }
}