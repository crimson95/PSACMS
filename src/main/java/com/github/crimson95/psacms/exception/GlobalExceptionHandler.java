package com.github.crimson95.psacms.exception;

import com.github.crimson95.psacms.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

// Centralizes API error handling so controllers and services can throw exceptions
// while this class converts them into consistent JSON responses.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles business-rule errors we throw manually,
    // such as "Application Not Found" or duplicate status updates.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),  // 400 Bad Request
                "Bad Request",
                ex.getMessage()  // Return the specific message from the exception.
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handles unexpected system errors, such as database failures or programming bugs.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),  // 500 Internal Server Error
                "Internal Server Error",
                "An unexpected error occurred. Please contact support."  // Safe generic message for the client.
        );

        // In production, log the real exception here so developers can diagnose it.
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
