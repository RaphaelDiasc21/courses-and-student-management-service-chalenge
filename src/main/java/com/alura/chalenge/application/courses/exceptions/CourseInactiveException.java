package com.alura.chalenge.application.courses.exceptions;

public class CourseInactiveException extends Exception {
    public CourseInactiveException(Long id) {
        super(String.format("Course with id %s is inactive",id));
    }
}
