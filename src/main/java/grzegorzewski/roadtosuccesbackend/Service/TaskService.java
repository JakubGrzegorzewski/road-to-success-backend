package grzegorzewski.roadtosuccesbackend.Service;

import grzegorzewski.roadtosuccesbackend.Model.RankInProgress;
import grzegorzewski.roadtosuccesbackend.Model.Task;
import grzegorzewski.roadtosuccesbackend.Repository.RankInProgressRepository;
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

    public Task findById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id " + id));
    }

    public List<Task> findAllTasksForRankInProgress(long rankInProgressId) {
        return rankInProgressRepository.findById(rankInProgressId)
                .map(RankInProgress::getTasks)
                .orElseThrow(() -> new EntityNotFoundException("RankInProgress not found with id " + rankInProgressId));
    }

    public Task save(Task task) {
        if (!taskRepository.existsById(task.getId())) {
            return taskRepository.save(task);
        } else {
            throw new EntityNotFoundException("Task already exists with id " + task.getId());
        }
    }

    public Task update(Task task) {
        if (taskRepository.existsById(task.getId())) {
            return taskRepository.save(task);
        } else {
            throw new EntityNotFoundException("Task not found with id " + task.getId());
        }
    }

    public void delete(long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Task not found with id " + id);
        }
    }
}
