package com.alura.chalenge.application.courses.exceptions;

import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;

public class CourseWithCodeNotFoundExceptionException extends EntityNotFoundException {
    public CourseWithCodeNotFoundExceptionException(String code) {
        super(String.format("Course with code %s not found",code));
    }
}
