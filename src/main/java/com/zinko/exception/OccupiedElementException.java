package com.zinko.exception;

public class OccupiedElementException extends RuntimeException {
    public OccupiedElementException(String message) {
        super(message);
    }

    public OccupiedElementException(String message, Throwable cause) {
        super(message, cause);
    }
}
