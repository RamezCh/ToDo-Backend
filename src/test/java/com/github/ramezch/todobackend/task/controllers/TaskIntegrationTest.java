package com.github.ramezch.todobackend.task.controllers;

import com.github.ramezch.todobackend.gpt.models.GPTAnswer;
import com.github.ramezch.todobackend.gpt.services.OpenAIService;
import com.github.ramezch.todobackend.task.models.Task;
import com.github.ramezch.todobackend.task.models.TaskStatus;
import com.github.ramezch.todobackend.task.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    TaskRepository taskRepo;

    @Test
    void getTasks_returnTasks() throws Exception {
        Task newTask = new Task("1", "hi", TaskStatus.OPEN);
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

}