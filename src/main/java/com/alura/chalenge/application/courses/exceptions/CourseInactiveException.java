package com.alura.chalenge.application.courses.exceptions;

public class CourseInactiveException extends Exception {
    public CourseInactiveException() {
        super("Course is inactive");
    }
}
