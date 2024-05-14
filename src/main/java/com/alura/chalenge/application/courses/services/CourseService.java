package com.alura.chalenge.application.courses.services;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.exceptions.CourseInactiveException;
import com.alura.chalenge.application.courses.exceptions.CourseNotFoundExceptionException;
import com.alura.chalenge.application.courses.exceptions.InactivateCourseException;
import com.alura.chalenge.application.shared.enums.Status;
import com.alura.chalenge.application.shared.interfaces.EntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService extends EntityService<Course> {
    void inactivateCourse(String code) throws InactivateCourseException;
    Course findById(Long id) throws CourseNotFoundExceptionException;
    Course findCourseByIdAndActive(Long id) throws CourseNotFoundExceptionException, CourseInactiveException;
    Page<Course> search(Status status, Pageable pageable);
}