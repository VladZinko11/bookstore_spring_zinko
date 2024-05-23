package com.zinko.exception;

public class EmptyRepositoryException extends RuntimeException {

    public EmptyRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRepositoryException(String message) {
        super(message);
    }
}
