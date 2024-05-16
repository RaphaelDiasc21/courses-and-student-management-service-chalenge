package com.alura.chalenge.application.courses.exceptions;

public class CourseWithCodeAlreadyRegisteredException extends Exception {
    public CourseWithCodeAlreadyRegisteredException(String code) {
        super(String.format("Course with code %s already registered",code));
    }
}
