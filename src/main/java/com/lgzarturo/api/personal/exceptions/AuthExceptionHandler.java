package com.lgzarturo.api.personal.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@Slf4j
public class AuthExceptionHandler implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public AuthExceptionHandler(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }
    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) {
        log.debug("Authentication failed: {}", authException.getMessage());
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}
