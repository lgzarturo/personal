package com.lgzarturo.api.personal.config;

import com.lgzarturo.api.personal.infrastructure.generic.InMemoryUniqueIdGenerator;
import com.lgzarturo.api.personal.infrastructure.generic.UniqueIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class AppBeans {
    @Bean
    public UniqueIdGenerator<UUID> uniqueIdGenerator() {
        return new InMemoryUniqueIdGenerator();
    }
}
