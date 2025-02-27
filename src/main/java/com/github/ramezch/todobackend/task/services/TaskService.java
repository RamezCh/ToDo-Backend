package com.github.ramezch.todobackend.task.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.ramezch.todobackend.gpt.models.GPTAnswer;
import com.github.ramezch.todobackend.task.exceptions.TaskNotFoundException;
import com.github.ramezch.todobackend.gpt.services.OpenAIService;
import com.github.ramezch.todobackend.task.models.TaskDTO;
import com.github.ramezch.todobackend.task.repositories.TaskRepository;
import com.github.ramezch.todobackend.task.models.Task;
import com.github.ramezch.todobackend.task.utils.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final IdService idService;
    private final OpenAIService aiService;

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public Task addTask(TaskDTO task) throws JsonProcessingException {
        GPTAnswer ai = aiService.checkSpelling(task.description());
        Task newTask;
        String uniqueID = idService.randomId();

        if(ai.isCorrect()) {
            newTask = new Task(uniqueID, task.description(), task.status());
        }
        else {
            newTask = new Task(uniqueID, ai.correctedVersion(), task.status());
        }

        return taskRepository.save(newTask);
    }

    public Task updateTask(String id, Task task) {
        getTaskById(id).orElseThrow(() -> new TaskNotFoundException("Task with ID: "+ id +" not found"));
        return taskRepository.save(task);
    }

    public void deleteTask(String id) {
        getTaskById(id).orElseThrow(() -> new TaskNotFoundException("Task with ID: "+ id +" not found"));
        taskRepository.deleteById(id);
    }

}
