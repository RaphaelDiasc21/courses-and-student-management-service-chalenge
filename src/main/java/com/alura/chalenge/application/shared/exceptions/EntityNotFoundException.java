package com.alura.chalenge.application.shared.exceptions;

public abstract class EntityNotFoundException extends  Exception {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
