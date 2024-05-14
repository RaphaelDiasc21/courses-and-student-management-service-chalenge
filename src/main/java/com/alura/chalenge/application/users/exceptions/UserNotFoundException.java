package com.alura.chalenge.application.users.exceptions;

import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long id) {
        super(String.format("User %s not found",id));
    }
}
