package com.alura.chalenge.application.users.exceptions;

public class InstructorNotFoundException extends Exception{
    public InstructorNotFoundException(Long id) {
        super(String.format("Instructor of id %s not found",id));
    }
}
