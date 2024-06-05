package com.zinko.exception;

public class BsException extends RuntimeException {
    public BsException() {
        super();
    }

    public BsException(String message) {
        super(message);
    }

    public BsException(String message, Throwable cause) {
        super(message, cause);
    }
}
