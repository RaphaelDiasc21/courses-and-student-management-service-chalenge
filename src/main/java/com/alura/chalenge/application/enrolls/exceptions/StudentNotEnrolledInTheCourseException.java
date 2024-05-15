package com.alura.chalenge.application.enrolls.exceptions;

public class StudentNotEnrolledInTheCourseException extends Exception {
    public StudentNotEnrolledInTheCourseException() {
        super("Student not enrolled in the course");
    }
}
