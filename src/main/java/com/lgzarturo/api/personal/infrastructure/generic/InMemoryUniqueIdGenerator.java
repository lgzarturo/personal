package com.lgzarturo.api.personal.infrastructure.generic;

import java.util.UUID;

public class InMemoryUniqueIdGenerator implements UniqueIdGenerator<UUID> {
    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }
}
