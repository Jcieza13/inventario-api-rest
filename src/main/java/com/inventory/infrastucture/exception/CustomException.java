package com.inventory.infrastucture.exception;

import lombok.Getter;

@Getter  // Genera automáticamente el método getStatusCode()
public class CustomException extends RuntimeException {
    private final int statusCode;

    public CustomException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}