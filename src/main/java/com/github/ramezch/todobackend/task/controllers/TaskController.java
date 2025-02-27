package com.github.ramezch.todobackend.task.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.ramezch.todobackend.task.models.TaskDTO;
import com.github.ramezch.todobackend.task.services.TaskService;
import com.github.ramezch.todobackend.task.models.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("{id}")
    public Task getTaskById(@PathVariable String id) {
        Optional<Task> task = taskService.getTaskById(id);
        if(task.isPresent())
            return task.get();
        throw new NoSuchElementException("Task with ID: "+ id +" not found");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTask(@RequestBody TaskDTO task) throws JsonProcessingException {
        return taskService.addTask(task);
    }

    @PutMapping("{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task task) throws JsonProcessingException {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
    }

}
