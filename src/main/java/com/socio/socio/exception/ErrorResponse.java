package com.socio.socio.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String error, String message, LocalDateTime timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }
}
