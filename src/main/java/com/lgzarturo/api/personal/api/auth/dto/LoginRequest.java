package com.lgzarturo.api.personal.api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @Email
    String username,
    @NotBlank
    String password) {
}
