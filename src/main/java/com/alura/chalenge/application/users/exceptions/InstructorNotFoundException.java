package com.alura.chalenge.application.users.exceptions;

import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;

public class InstructorNotFoundException extends EntityNotFoundException {
    public InstructorNotFoundException(String email) {
        super(String.format("Instructor of email %s not found",email));
    }
}
