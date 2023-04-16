package com.lgzarturo.api.personal.api.user.mapper;

import com.lgzarturo.api.personal.api.user.User;
import com.lgzarturo.api.personal.api.user.dto.UserResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserResponseMapper implements Function<User, UserResponse> {
    @Override
    public UserResponse apply(User user) {
        return new UserResponse(
            user.getUsername(),
            user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
        );
    }
}
