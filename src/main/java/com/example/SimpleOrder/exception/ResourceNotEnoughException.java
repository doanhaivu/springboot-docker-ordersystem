package com.example.SimpleOrder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
public class ResourceNotEnoughException extends RuntimeException {
    public ResourceNotEnoughException() {
        super();
    }

    public ResourceNotEnoughException(String message) {
        super(message);
    }

    public ResourceNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }
}