package com.lgzarturo.api.personal.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ApiError(
    String path,
    String message,
    int statusCode,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime timestamp) {
    public ApiError(String path, String message, int statusCode) {
        this(path, message, statusCode, LocalDateTime.now());
    }
}
