package com.github.ramezch.todobackend.task;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public Optional<Task> getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTask(@RequestBody TaskDTO task) {
        return taskService.addTask(task);
    }

    @PutMapping("{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
    }

}
