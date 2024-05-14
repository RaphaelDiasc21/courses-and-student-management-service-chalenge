 package com.alura.chalenge.application.courses.services;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.CourseRepository;
import com.alura.chalenge.application.courses.exceptions.*;
import com.alura.chalenge.application.courses.specifications.SearchCourseSpecification;
import com.alura.chalenge.application.shared.enums.Status;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

 @Service
public class CourseServiceImpl implements CourseService {
    final CourseRepository courseRepository;
    final UserService userService;

    CourseServiceImpl(CourseRepository courseRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    @Override
    public Course create(Course course) throws EntityCreationException, EntityNotFoundException {
        try {
            isCodeAlreadyRegistered(course.getCode());
            User user = userService.findInstructorByEmail(course.getInstructor().getEmail());
            course.setInstructor(user);

            return courseRepository.save(course);
        } catch(CourseWithCodeAlreadyRegisteredException exception) {
            throw new EntityCreationException(exception.getMessage());
        }
    }

    @Override
    public void inactivateCourse(String code) throws InactivateCourseException {
        try {
            Course course = findByCode(code);

            course.setStatus(Status.INACTIVE);
            course.setInactiveDate(LocalDate.now());

            courseRepository.save(course);
        } catch(CourseWithCodeNotFoundExceptionException exception) {
            throw new InactivateCourseException(exception.getMessage());
        }
    }

     @Override
     public Page<Course> search(Status status, Pageable pageable) {
         SearchCourseSpecification searchCourseSpecification = new SearchCourseSpecification(status);

        return courseRepository.findAll(searchCourseSpecification,pageable);
    }

    @Override
    public Course findById(Long id) throws CourseNotFoundExceptionException {
        return courseRepository
                .findById(id)
                .orElseThrow(() -> new CourseNotFoundExceptionException(id));
    }

    @Override
    public Course findCourseByIdAndActive(Long id) throws CourseNotFoundExceptionException, CourseInactiveException {
        Course course = this.findById(id);
        if(course.getStatus().equals(Status.INACTIVE)) throw new CourseInactiveException(id);
        return course;
    }

    private void isCodeAlreadyRegistered(String code) throws CourseWithCodeAlreadyRegisteredException {
        try {
            findByCode(code);
        } catch(CourseWithCodeNotFoundExceptionException exception) {
            return;
        }
       throw new CourseWithCodeAlreadyRegisteredException(code);
    }

    private Course findByCode(String code) throws CourseWithCodeNotFoundExceptionException {
         return courseRepository.findByCode(code)
                 .orElseThrow(() -> new CourseWithCodeNotFoundExceptionException(code));
    }
}
