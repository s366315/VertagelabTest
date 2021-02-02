package com.vertagelab.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RequestException extends RuntimeException{
    private String message;

    public RequestException() {

    }

    public RequestException(String message) {
        this.message = message;
    }
}
