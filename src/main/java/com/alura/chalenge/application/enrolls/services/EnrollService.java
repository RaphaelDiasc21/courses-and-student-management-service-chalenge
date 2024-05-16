package com.alura.chalenge.application.enrolls.services;

import com.alura.chalenge.application.enrolls.Enroll;
import com.alura.chalenge.application.enrolls.exceptions.StudentNotEnrolledInTheCourseException;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.interfaces.EntityService;

import java.util.List;

public interface EnrollService  {
    List<Enroll> findByCourseId(Long courseId);
    Enroll create(String studentIdentification,String courseCode) throws EntityCreationException;
    Enroll findByStudentIdAndCourseCode(Long studentId,String courseCode) throws StudentNotEnrolledInTheCourseException;
}
