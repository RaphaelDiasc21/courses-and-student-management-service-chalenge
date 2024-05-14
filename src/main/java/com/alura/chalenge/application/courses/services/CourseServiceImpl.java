 package com.alura.chalenge.application.courses.services;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.CourseRepository;
import com.alura.chalenge.application.courses.exceptions.*;
import com.alura.chalenge.application.shared.enums.Status;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.exceptions.InstructorNotFoundException;
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
    public Course create(Course course) throws EntityCreationException {
        try {
            isCodeAlreadyRegistered(course.getCode());
            User user = userService.findInstructorById(course.getInstructor().getId());
            course.setInstructor(user);

            return courseRepository.save(course);
        } catch(InstructorNotFoundException | CourseWithCodeAlreadyRegisteredException exception) {
            throw new EntityCreationException(exception.getMessage());
        }
    }

    @Override
    public Course inactivateCourse(String code) throws InactivateCourseException {
        try {
            Course course = findByCode(code);

            course.setStatus(Status.INACTIVE);
            course.setInactiveDate(LocalDate.now());

            return courseRepository.save(course);
        } catch(CourseWithCodeNotFoundException exception) {
            throw new InactivateCourseException(exception.getMessage());
        }
    }

     @Override
     public boolean isCourseActive(Long id) throws CourseNotFoundException {
          Course course = findById(id);
          return course.getStatus().equals(Status.ACTIVE);
     }

     public Page<Course> search(Status status, Pageable pageable) {
        return courseRepository.findByStatus(status,pageable);
    }

    @Override
    public Course findById(Long id) throws CourseNotFoundException {
        return courseRepository
                .findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Override
    public Course findCourseByIdAndActive(Long id) throws CourseNotFoundException, CourseInactiveException {
        Course course = this.findById(id);
        if(course.getStatus().equals(Status.INACTIVE)) throw new CourseInactiveException(id);
        return course;
    }

    private void isCodeAlreadyRegistered(String code) throws CourseWithCodeAlreadyRegisteredException {
        try {
            findByCode(code);
        } catch(CourseWithCodeNotFoundException exception) {
            return;
        }
       throw new CourseWithCodeAlreadyRegisteredException(code);
    }

    private Course findByCode(String code) throws CourseWithCodeNotFoundException {
         return courseRepository.findByCode(code)
                 .orElseThrow(() -> new CourseWithCodeNotFoundException(code));
    }
}
