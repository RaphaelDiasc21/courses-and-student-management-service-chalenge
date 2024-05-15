 package com.alura.chalenge.application.courses.services;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.CourseRepository;
import com.alura.chalenge.application.courses.exceptions.*;
import com.alura.chalenge.application.courses.projections.CourseIdProjection;
import com.alura.chalenge.application.courses.specifications.SearchCourseSpecification;
import com.alura.chalenge.application.shared.enums.Role;
import com.alura.chalenge.application.shared.enums.Status;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.exceptions.UserNotFoundByEmailException;
import com.alura.chalenge.application.users.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

 @Service
public class CourseServiceImpl implements CourseService {
    final CourseRepository courseRepository;
    final UserService userService;

    CourseServiceImpl(CourseRepository courseRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public Course create(Course course, String instructorIdentification) throws EntityCreationException, EntityNotFoundException {
        try {
            isCodeAlreadyRegistered(course.getCode());
            User user = userService.findUserByEmailOrUsername(instructorIdentification);
            isUserNotAInstructor(user);

            course.setInstructor(user);
            return courseRepository.save(course);
        } catch(UserNotFoundByEmailException | UserIsNotAInstructorException | CourseWithCodeAlreadyRegisteredException exception) {
            throw new EntityCreationException(exception.getMessage());
        }
    }

    private void isUserNotAInstructor(User user) throws UserIsNotAInstructorException  {
        if(user.getRole().equals(Role.ESTUDANTE) || user.getRole().equals(Role.ADMIN)) {
            throw new UserIsNotAInstructorException();
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
    public Course findCourseByCodeAndActive(String code) throws CourseInactiveException, CourseWithCodeNotFoundExceptionException {
        Course course = this.findByCode(code);
        if(course.getStatus().equals(Status.INACTIVE)) throw new CourseInactiveException();
        return course;
    }

    @Override
    public List<Long> findCoursesIds() {
        List<CourseIdProjection> courseIdProjectionList = courseRepository.findCoursesIds();
        return courseIdProjectionList.stream()
                .map(courseIdProjection -> courseIdProjection.getId())
                .collect(Collectors.toList());
    }
    private void isCodeAlreadyRegistered(String code) throws CourseWithCodeAlreadyRegisteredException {
        try {
            findByCode(code);
        } catch(CourseWithCodeNotFoundExceptionException exception) {
            return;
        }
       throw new CourseWithCodeAlreadyRegisteredException(code);
    }

    @Override
    public Course findByCode(String code) throws CourseWithCodeNotFoundExceptionException {
         return courseRepository.findByCode(code)
                 .orElseThrow(() -> new CourseWithCodeNotFoundExceptionException(code));
    }
}
