package com.zinko.service.exception;

public class EmptyRepositoryException extends BookStoreException {
    public EmptyRepositoryException(String message) {
        super(message);
    }
}
