package com.example.kafkacore.exception;

public class NonRetryableException extends RuntimeException {
    public NonRetryableException(Exception exception) {
        super(exception);
    }

    public NonRetryableException(String message) {
        super(message);
    }
}
