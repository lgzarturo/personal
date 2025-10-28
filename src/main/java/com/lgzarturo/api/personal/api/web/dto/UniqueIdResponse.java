package com.lgzarturo.api.personal.api.web.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UniqueIdResponse(String uniqueId, LocalDateTime timestamp) {
    public UniqueIdResponse(UUID uniqueId) {
        this(uniqueId.toString(), LocalDateTime.now());
    }
}
