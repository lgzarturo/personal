package com.lgzarturo.api.personal.api.auth;

import com.lgzarturo.api.personal.api.auth.dto.LoginRequest;
import com.lgzarturo.api.personal.api.auth.dto.LoginResponse;
import com.lgzarturo.api.personal.api.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(
        @RequestBody @Valid LoginRequest loginRequest
    ) {
        log.info("Login request: {}", loginRequest);
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, response.token())
            .body(response);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
