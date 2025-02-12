package com.ev.realestate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<ErrorResponse> handleTooManyRequests(TooManyRequestsException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.TOO_MANY_REQUESTS, ex, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex, request);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus status, RuntimeException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(status, ex.getMessage(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(error, status);
    }
}
