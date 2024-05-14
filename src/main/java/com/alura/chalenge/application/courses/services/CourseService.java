package com.alura.chalenge.application.courses.services;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.exceptions.CourseInactiveException;
import com.alura.chalenge.application.courses.exceptions.CourseNotFoundException;
import com.alura.chalenge.application.courses.exceptions.InactivateCourseException;
import com.alura.chalenge.application.shared.interfaces.EntityService;

public interface CourseService extends EntityService<Course> {
    Course inactivateCourse(String code) throws InactivateCourseException;
    boolean isCourseActive(Long id) throws CourseNotFoundException;
    Course findById(Long id) throws CourseNotFoundException;
    Course findCourseByIdAndActive(Long id) throws CourseNotFoundException, CourseInactiveException;
}