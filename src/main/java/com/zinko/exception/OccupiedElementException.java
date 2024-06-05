package com.zinko.exception;

public class OccupiedElementException extends BsException {
    public OccupiedElementException() {
        super();
    }

    public OccupiedElementException(String message) {
        super(message);
    }

    public OccupiedElementException(String message, Throwable cause) {
        super(message, cause);
    }
}
