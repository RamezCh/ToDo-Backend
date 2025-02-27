package com.github.ramezch.todobackend.task.controllers;

import com.github.ramezch.todobackend.task.models.Task;
import com.github.ramezch.todobackend.task.models.TaskStatus;
import com.github.ramezch.todobackend.task.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    TaskRepository taskRepo;

    Task newTask;

    @BeforeEach
    void setup() {
        newTask = new Task("1", "hi", TaskStatus.OPEN);
    }

    @Test
    void getTasks_returnTasks() throws Exception {
        Task newTask2 = new Task("2", "hi", TaskStatus.OPEN);
        taskRepo.save(newTask);
        taskRepo.save(newTask2);

        // WHEN
        mvc.perform(get("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
            [
                {
                    "description": "hi",
                    "status": "OPEN"
                },
                {
                    "description": "hi",
                    "status": "OPEN"
                }
            ]"""));
    }

    @Test
    void getTaskById_returnTask() throws Exception {
        Task newTask = new Task("1", "hi", TaskStatus.OPEN);
        taskRepo.save(newTask);

        // WHEN
        mvc.perform(get("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {
                    "description": "hi",
                    "status": "OPEN"
                }
            """));
    }

    @Test
    void deleteTask_returnTask() throws Exception {
        Task newTask = new Task("1", "hi", TaskStatus.OPEN);
        taskRepo.save(newTask);

        // WHEN
        mvc.perform(delete("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isNoContent());
    }

    @Test
    void getTaskById_NonExistentTask_ReturnsNotFound() throws Exception {
        // WHEN
        mvc.perform(get("/api/todo/3")
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTask_NonExistentTask_ReturnsNotFound() throws Exception {
        // WHEN
        mvc.perform(delete("/api/todo/2")
                        .contentType(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().isNotFound());
    }

}