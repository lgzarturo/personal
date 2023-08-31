package com.lgzarturo.api.personal.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartProject {

    @Bean
    public CommandLineRunner run() {
        return args -> {
            log.info("-- Starting project...");
        };
    }
}
