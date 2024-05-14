package com.alura.chalenge.application.courses.exceptions;

public class CourseWithCodeNotFoundException extends Exception {
    public CourseWithCodeNotFoundException(String code) {
        super(String.format("Course with code %s not found",code));
    }
}
