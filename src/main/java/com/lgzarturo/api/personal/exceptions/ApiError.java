package com.lgzarturo.api.personal.exceptions;

import java.time.LocalDateTime;

public record ApiError(String path, String message, int statusCode, LocalDateTime timestamp) {
    public ApiError(String path, String message, int statusCode) {
        this(path, message, statusCode, LocalDateTime.now());
    }
}
