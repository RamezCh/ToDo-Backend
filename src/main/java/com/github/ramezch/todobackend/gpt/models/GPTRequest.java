package com.github.ramezch.todobackend.gpt.models;

import java.util.List;

public record GPTRequest(String model, List<RequestMessage> messages, double temperature) {
}
