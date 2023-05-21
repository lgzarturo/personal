package com.lgzarturo.api.personal.api.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @GetMapping
    public String list() {
        return "Hello World";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id) {
        log.debug("Get user by id: {}", id);
        return "Hello World";
    }
}
