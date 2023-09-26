package com.nibm.smartmedicine.exception;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ApiExceptionResponse {
    private final String error;
    private final String message;
    private final int status;
    private final ZonedDateTime timestamp;
    private final String path;

    public ApiExceptionResponse(String error, String message, int status, ZonedDateTime timestamp, String path) {
        this.error = error;
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.path = path;
    }
}
