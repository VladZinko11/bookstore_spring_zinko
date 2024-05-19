package com.zinko.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyRuntimeException extends RuntimeException {
    private Integer status;

    public MyRuntimeException(String message) {
        super(message);
    }

    public MyRuntimeException(String message, Integer status) {
        super(message);
        this.status=status;
    }

    public MyRuntimeException(String message, Throwable cause, Integer status) {
        super(message, cause);
        this.status=status;
    }
}
