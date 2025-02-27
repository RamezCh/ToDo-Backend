package com.github.ramezch.todobackend.gpt.models;

import java.util.List;

public record GPTResponse(List<Choices> choices) {
}
