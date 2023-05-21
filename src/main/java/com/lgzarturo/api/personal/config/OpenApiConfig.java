package com.lgzarturo.api.personal.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "Personal API",
        version = "v1.0.0",
        description = "a REST API for personal management and finance, built with Spring Boot and Spring Data JPA",
        contact = @Contact(
            name = "Arturo López Gómez",
            email = "lgzarturo@gmail.com",
            url = "https://lgzarturo.com"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Local server"
        )
    }
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT authentication",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
