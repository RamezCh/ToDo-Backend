package com.github.ramezch.todobackend.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final IdService idService;

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public Task addTask(TaskDTO task) {
        String uniqueID = idService.randomId();
        Task newTask = new Task(uniqueID, task.description(), task.status());
        return taskRepository.save(newTask);
    }

    public Task updateTask(String id, Task task) {
        getTaskById(id).orElseThrow(() -> new TaskNotFoundException("Task with ID: "+ id +" not found"));
        return taskRepository.save(task);
    }

    public Task deleteTask(String id) {
        Task toBeDeleted = getTaskById(id).orElseThrow(() -> new TaskNotFoundException("Task with ID: "+ id +" not found"));
        taskRepository.deleteById(id);
        return toBeDeleted;
    }

}
