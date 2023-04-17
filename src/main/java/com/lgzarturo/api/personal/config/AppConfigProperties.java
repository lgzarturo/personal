package com.lgzarturo.api.personal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppConfigProperties(String name, String version, String description, String url, String email) {
}
