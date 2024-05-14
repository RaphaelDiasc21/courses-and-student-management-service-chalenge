package com.alura.chalenge.application.courses.exceptions;

public class CourseNotFoundException extends Exception{
    public CourseNotFoundException(Long id) {
        super(String.format("Course id %s not found",id));
    }
}
