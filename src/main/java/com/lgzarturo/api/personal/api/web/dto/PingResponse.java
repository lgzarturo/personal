package com.lgzarturo.api.personal.api.web.dto;

import java.time.LocalDateTime;

public record PingResponse(String message, LocalDateTime timestamp) {
    public PingResponse(int counter) {
        this("Pong #" + counter, LocalDateTime.now());
    }
}
