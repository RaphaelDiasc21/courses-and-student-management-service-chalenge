package com.alura.chalenge.application.enrolls.services;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.exceptions.CourseInactiveException;
import com.alura.chalenge.application.courses.exceptions.CourseWithCodeNotFoundExceptionException;
import com.alura.chalenge.application.courses.services.CourseService;
import com.alura.chalenge.application.enrolls.Enroll;
import com.alura.chalenge.application.enrolls.EnrollRepository;
import com.alura.chalenge.application.enrolls.exceptions.StudentAlreadyEnrolledInTheCourseException;
import com.alura.chalenge.application.enrolls.exceptions.StudentNotEnrolledInTheCourseException;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.exceptions.UserNotFoundByEmailException;
import com.alura.chalenge.application.users.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollServiceImpl implements EnrollService {
    private final EnrollRepository enrollRepository;
    private final CourseService courseService;
    private final UserService userService;

    EnrollServiceImpl(EnrollRepository enrollRepository, CourseService courseService, UserService userService) {
        this.enrollRepository = enrollRepository;
        this.courseService = courseService;
        this.userService = userService;
    }
    @Override
    public Enroll create(String studentIdentification, String courseCode) throws EntityCreationException {
        try {
            isStudentAlreadyEnrolledInTheCourse(studentIdentification,courseCode);

            User student = userService.findUserByEmailOrUsername(studentIdentification);
            Course course = courseService.findCourseByCodeAndActive(courseCode);

            Enroll enroll = new Enroll();
            enroll.setCourse(course);
            enroll.setStudent(student);

            return enrollRepository.save(enroll);
        } catch(CourseInactiveException | UserNotFoundByEmailException | CourseWithCodeNotFoundExceptionException | StudentAlreadyEnrolledInTheCourseException exception) {
            throw new EntityCreationException(exception.getMessage());
        }
    }

    @Override
    public List<Enroll> findByCourseId(Long courseId) {
        return enrollRepository.findByCourse_Id(courseId);
    }

    @Override
    public Enroll findByStudentIdAndCourseCode(Long studentId,String courseCode) throws StudentNotEnrolledInTheCourseException {
            return enrollRepository.findByStudentIdAndCourseCode(studentId,courseCode)
                    .orElseThrow(() -> new StudentNotEnrolledInTheCourseException());
    }

    private void isStudentAlreadyEnrolledInTheCourse(String studentIdentification,String courseCode) throws StudentAlreadyEnrolledInTheCourseException {
        Optional<Enroll> enrollOptional = enrollRepository.findByStudentAndCourse(
                courseCode,
                studentIdentification
        );

        if(enrollOptional.isPresent()) throw new StudentAlreadyEnrolledInTheCourseException();
    }
}
