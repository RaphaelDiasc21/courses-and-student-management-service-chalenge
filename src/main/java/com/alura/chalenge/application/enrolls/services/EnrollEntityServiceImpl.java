package com.alura.chalenge.application.enrolls.services;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.exceptions.CourseInactiveException;
import com.alura.chalenge.application.courses.exceptions.CourseNotFoundExceptionException;
import com.alura.chalenge.application.courses.services.CourseService;
import com.alura.chalenge.application.enrolls.Enroll;
import com.alura.chalenge.application.enrolls.EnrollRepository;
import com.alura.chalenge.application.enrolls.exceptions.StudentAlreadyEnrolledInTheCourseException;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.exceptions.UserNotFoundByEmailException;
import com.alura.chalenge.application.users.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollEntityServiceImpl implements EnrollService {
    private final EnrollRepository enrollRepository;
    private final CourseService courseService;
    private final UserService userService;

    EnrollEntityServiceImpl(EnrollRepository enrollRepository, CourseService courseService, UserService userService) {
        this.enrollRepository = enrollRepository;
        this.courseService = courseService;
        this.userService = userService;
    }

    @Override
    public Enroll create(Enroll enroll) throws EntityCreationException {
        try {
            isStudentAlreadyEnrolledInTheCourse(enroll);

            User student = userService.findUserByEmailOrUsername(enroll.getStudent().getEmail());
            Course course = courseService.findCourseByIdAndActive(enroll.getCourse().getId());

            enroll.setCourse(course);
            enroll.setStudent(student);

            return enrollRepository.save(enroll);
        } catch(CourseInactiveException | UserNotFoundByEmailException | CourseNotFoundExceptionException | StudentAlreadyEnrolledInTheCourseException exception) {
            throw new EntityCreationException(exception.getMessage());
        }
    }

    private void isStudentAlreadyEnrolledInTheCourse(Enroll enroll) throws StudentAlreadyEnrolledInTheCourseException {
        Optional<Enroll> enrollOptional = enrollRepository.findByStudentAndCourse(
                enroll.getCourse().getId(),
                enroll.getStudent().getEmail()
        );

        if(enrollOptional.isPresent()) throw new StudentAlreadyEnrolledInTheCourseException();
    }

    @Override
    public List<Enroll> findByCourseId(Long courseId) {
        return enrollRepository.findByCourse_Id(courseId);
    }
}
