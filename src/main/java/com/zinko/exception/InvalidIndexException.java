package com.zinko.exception;

public class InvalidIndexException extends BsException {
    public InvalidIndexException() {
        super();
    }

    public InvalidIndexException(String message) {
        super(message);
    }

    public InvalidIndexException(String message, Throwable cause) {
        super(message, cause);
    }
}
