package com.alura.chalenge.application.enrolls.exceptions;

public class StudentAlreadyEnrolledInTheCourseException extends Exception{
    public StudentAlreadyEnrolledInTheCourseException() {
        super("Student already enrolled in the course");
    }
}
