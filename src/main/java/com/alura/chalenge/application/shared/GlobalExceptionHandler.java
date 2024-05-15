package com.alura.chalenge.application.shared;

import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> message = new ArrayList<String>();

        List<String> collect = ex.getBindingResult().getFieldErrors().stream().filter(Objects::nonNull)
                .map(m -> (m.getField() + " " + m.getDefaultMessage())).collect(Collectors.toList());
        message.addAll(collect);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,message.toString());
        problemDetail.setProperty("Timestamp", Instant.now());
        return new ResponseEntity<Object>(problemDetail, HttpStatus.BAD_REQUEST);
    }
}
