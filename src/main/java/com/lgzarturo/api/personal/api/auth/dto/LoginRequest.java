package com.lgzarturo.api.personal.api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @Email(message = "The value must be a valid email address")
    @NotBlank(message = "The field Email is required")
    String username,
    @NotBlank(message = "The field password is required")
    String password) {
}
