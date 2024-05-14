package com.alura.chalenge.application.shared;

import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = EntityCreationException.class)
    protected ProblemDetail handleEntityCreationException(EntityCreationException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,exception.getMessage());
        problemDetail.setTitle("Creation exception");
        problemDetail.setProperty("Timestamp", Instant.now());
        return problemDetail;
    }


    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ProblemDetail handleEntityNotFoundException(EntityNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,exception.getMessage());
        problemDetail.setTitle("Not found exception");
        problemDetail.setProperty("Timestamp", Instant.now());
        return problemDetail;
    }
}
