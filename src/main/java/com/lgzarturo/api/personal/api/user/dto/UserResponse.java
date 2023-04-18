package com.lgzarturo.api.personal.api.user.dto;

import java.util.List;

public record UserResponse(
    String username,
    List<String> roles) {
}
