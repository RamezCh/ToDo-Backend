package com.github.ramezch.todobackend.exceptions;

import java.time.LocalDateTime;

public record ErrorMessage(String message, LocalDateTime timestamp) {
}
