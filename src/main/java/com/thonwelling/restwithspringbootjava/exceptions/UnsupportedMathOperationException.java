package com.thonwelling.restwithspringbootjava.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedMathOperationException extends RuntimeException {
    public UnsupportedMathOperationException(String ex) {
        super(ex);
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
