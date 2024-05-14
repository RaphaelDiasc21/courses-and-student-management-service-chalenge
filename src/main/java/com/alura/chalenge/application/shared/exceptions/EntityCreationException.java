package com.alura.chalenge.application.shared.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class EntityCreationException extends Exception {
    private HttpStatus httpStatus;

    public EntityCreationException(String message) {
        super(message);
    }

    public EntityCreationException(String message,HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
