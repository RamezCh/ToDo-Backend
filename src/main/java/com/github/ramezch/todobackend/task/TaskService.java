package com.github.ramezch.todobackend.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final IdService idService;

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task addTask(TaskDTO task) {
        String uniqueID = idService.randomId();
        Task newTask = new Task(uniqueID, task.description(), task.status());
        return taskRepository.save(newTask);
    }
}
