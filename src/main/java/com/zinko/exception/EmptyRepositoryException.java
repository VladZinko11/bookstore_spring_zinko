package com.zinko.exception;

public class EmptyRepositoryException extends RuntimeException {
    public EmptyRepositoryException(String booksDirectoryIsEmpty) {
    }
}
