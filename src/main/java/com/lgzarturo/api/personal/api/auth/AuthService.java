package com.lgzarturo.api.personal.api.auth;

import com.lgzarturo.api.personal.api.auth.dto.LoginRequest;
import com.lgzarturo.api.personal.api.auth.dto.LoginResponse;
import com.lgzarturo.api.personal.api.user.User;
import com.lgzarturo.api.personal.api.user.dto.UserResponse;
import com.lgzarturo.api.personal.api.user.mapper.UserResponseMapper;
import com.lgzarturo.api.personal.security.jwt.JwtGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserResponseMapper userResponseMapper;

    public AuthService(
        AuthenticationManager authenticationManager,
        JwtGenerator jwtGenerator,
        UserResponseMapper userResponseMapper
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userResponseMapper = userResponseMapper;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        log.debug("Login request: {}", loginRequest);
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.username(),
                loginRequest.password()
            )
        );
        User user = (User) authentication.getPrincipal();
        UserResponse userResponse = userResponseMapper.apply(user);
        String token = jwtGenerator.issueToken(userResponse.username(), userResponse.roles());
        log.debug("Login response: {}", token);
        return new LoginResponse(token, userResponse);
    }

    public UserResponse getCurrentUser() {
        log.debug("Get current user" );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userResponseMapper.apply((User) authentication.getPrincipal());
    }
}
