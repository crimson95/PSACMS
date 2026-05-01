package com.github.crimson95.psacms.dto;

import java.time.LocalDateTime;

// Standard JSON shape returned when the API reports an error.
public class ErrorResponse {
    // When the error response was created.
    private LocalDateTime timestamp;
    // Numeric HTTP status code, for example 400 or 500.
    private int status;
    // Short HTTP error name, for example "Bad Request".
    private String error;
    // Human-readable explanation for the client.
    private String message;

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
