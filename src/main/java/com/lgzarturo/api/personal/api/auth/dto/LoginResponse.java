package com.lgzarturo.api.personal.api.auth.dto;

import com.lgzarturo.api.personal.api.user.dto.UserResponse;

public record LoginResponse(String token, UserResponse user) {
}
