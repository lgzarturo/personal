package com.lgzarturo.api.personal.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ResourceDuplicatedException.class)
    public ResponseEntity<ApiError> handleException(
        ResourceDuplicatedException exception,
        HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.CONFLICT;
        return getResponseEntity(exception, request, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleException(
        ResourceNotFoundException exception,
        HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return getResponseEntity(exception, request, status);
    }

    @ExceptionHandler(ResourceValidationException.class)
    public ResponseEntity<ApiError> handleException(
        ResourceValidationException exception,
        HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return getResponseEntity(exception, request, status);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiError> handleException(
        InsufficientAuthenticationException exception,
        HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return getResponseEntity(exception, request, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleException(
        BadCredentialsException exception,
        HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return getResponseEntity(exception, request, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(
        Exception exception,
        HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return getResponseEntity(exception, request, status);
    }

    private ResponseEntity<ApiError> getResponseEntity(
        Exception exception,
        HttpServletRequest request,
        HttpStatus status
    ) {
        ApiError apiError = new ApiError(
            request.getRequestURI(),
            exception.getMessage(),
            status.value()
        );
        return ResponseEntity.status(status).body(apiError);
    }
}
