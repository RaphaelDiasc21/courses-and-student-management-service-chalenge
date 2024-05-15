package com.alura.chalenge.application.users.exceptions;

import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;

public class UserNotFoundByEmailException extends EntityNotFoundException {
    public UserNotFoundByEmailException(String email) {
        super(String.format("User with email %s not found",email));
    }
}
