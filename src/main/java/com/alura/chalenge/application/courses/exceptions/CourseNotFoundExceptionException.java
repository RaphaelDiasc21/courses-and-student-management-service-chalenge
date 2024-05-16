package com.alura.chalenge.application.courses.exceptions;

import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;

public class CourseNotFoundExceptionException extends EntityNotFoundException {
    public CourseNotFoundExceptionException(Long id) {
        super(String.format("Course id %s not found",id));
    }
}
