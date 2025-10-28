package com.lgzarturo.api.personal.api.web.dto;

import com.lgzarturo.api.personal.config.AppConfigProperties;

public record AppInfoResponse(String name, String version, String description, String url, String email) {
    public AppInfoResponse(AppConfigProperties appConfigProperties) {
        this(appConfigProperties.name(), appConfigProperties.version(), appConfigProperties.description(), appConfigProperties.url(), appConfigProperties.email());
    }
}
