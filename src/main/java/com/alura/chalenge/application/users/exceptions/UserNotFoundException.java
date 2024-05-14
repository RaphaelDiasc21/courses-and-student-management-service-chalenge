package com.alura.chalenge.application.users.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Long id) {
        super(String.format("User %s not found",id));
    }
}
