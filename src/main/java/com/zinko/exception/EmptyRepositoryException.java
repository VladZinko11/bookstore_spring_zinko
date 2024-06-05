package com.zinko.exception;

public class EmptyRepositoryException extends BsException {
    public EmptyRepositoryException() {
        super();
    }

    public EmptyRepositoryException(String message) {
        super(message);
    }

    public EmptyRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
