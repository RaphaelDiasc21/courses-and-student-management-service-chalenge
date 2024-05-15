package com.alura.chalenge.application.courses.exceptions;

public class UserIsNotAInstructorException extends Exception {
    public UserIsNotAInstructorException() {
        super("InstructorIdentification provided, not correspond of a Instructor");
    }
}
