package com.alura.chalenge.application.users.exceptions;

public class EmailOrUsernameAlreadyRegisteredException extends Exception{
    public EmailOrUsernameAlreadyRegisteredException() {
        super("Email or username already registered");
    }
}
