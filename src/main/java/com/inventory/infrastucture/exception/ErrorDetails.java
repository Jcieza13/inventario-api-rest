package com.inventory.infrastucture.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter // Genera automáticamente los métodos getter para todos los campos
public class ErrorDetails {
    private final LocalDateTime timestamp;
    private final String message;
    private final String details;
    private final int status;

    public ErrorDetails(LocalDateTime timestamp, String message, String details, int status) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.status = status;
    }

}