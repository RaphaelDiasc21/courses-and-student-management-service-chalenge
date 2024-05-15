package com.alura.chalenge.application.courses.services;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.exceptions.CourseInactiveException;
import com.alura.chalenge.application.courses.exceptions.CourseNotFoundExceptionException;
import com.alura.chalenge.application.courses.exceptions.CourseWithCodeNotFoundExceptionException;
import com.alura.chalenge.application.courses.exceptions.InactivateCourseException;
import com.alura.chalenge.application.shared.enums.Status;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import com.alura.chalenge.application.shared.interfaces.EntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
    Course create(Course course, String instructorIdentification) throws EntityCreationException, EntityNotFoundException;
    void inactivateCourse(String code) throws InactivateCourseException;
    Course findById(Long id) throws CourseNotFoundExceptionException;
    Course findCourseByCodeAndActive(String code) throws CourseInactiveException, CourseWithCodeNotFoundExceptionException;
    Page<Course> search(Status status, Pageable pageable);
    List<Long> findCoursesIds();
    Course findByCode(String code) throws CourseWithCodeNotFoundExceptionException;
}