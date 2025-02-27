package com.github.ramezch.todobackend.gpt.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ramezch.todobackend.gpt.models.GPTAnswer;
import com.github.ramezch.todobackend.gpt.models.GPTRequest;
import com.github.ramezch.todobackend.gpt.models.GPTResponse;
import com.github.ramezch.todobackend.gpt.models.RequestMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class OpenAIService {

    private final RestClient restClient;
    private final ObjectMapper mapper;

    public OpenAIService(@Value("${OPEN_AI_KEY}") String key, RestClient.Builder restClient, ObjectMapper mapper) {
        this.mapper = mapper;
        this.restClient = restClient
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + key)
                .build();
    }

    public GPTAnswer checkSpelling(String task) throws JsonProcessingException {
        String input = "Check if there are any spelling mistakes. Return JSON without ```json at start that has following fields isCorrect, correctedVersion. " + task;
        GPTResponse response = restClient.post()
                .body(new GPTRequest("gpt-4-turbo",
                        List.of(new RequestMessage("user", input)),
                        0.7))
                .retrieve()
                .body(GPTResponse.class);

        // READ -> JSON to Java Object
        // write -> Java Obj to JSON
        GPTAnswer answer = mapper.readValue(response.choices().get(0).message().content(), GPTAnswer.class);


        return answer;
    }
}
