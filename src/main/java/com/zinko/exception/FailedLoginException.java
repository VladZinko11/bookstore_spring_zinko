package com.zinko.exception;

public class FailedLoginException extends RuntimeException {
    public FailedLoginException(String message) {
        super(message);
    }

    public FailedLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
