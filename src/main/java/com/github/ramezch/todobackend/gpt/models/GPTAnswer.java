package com.github.ramezch.todobackend.gpt.models;

public record GPTAnswer(boolean isCorrect, String correctedVersion) {
}
