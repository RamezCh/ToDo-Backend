package com.github.ramezch.todobackend.gpt.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.ramezch.todobackend.gpt.models.GPTAnswer;
import com.github.ramezch.todobackend.gpt.services.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class OpenAIController {
    private final OpenAIService service;

    @PostMapping
    public GPTAnswer generateAiAnswer(@RequestBody String task) throws JsonProcessingException {
        return service.checkSpelling(task);
    }
}
