package com.alura.chalenge.application.rates.exceptions;

public class RatingNotDoingByStudentException extends Exception {
    public RatingNotDoingByStudentException() {
        super("Rating not being doing by a student");
    }
}
