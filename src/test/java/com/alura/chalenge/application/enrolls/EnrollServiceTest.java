package com.alura.chalenge.application.enrolls;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.exceptions.CourseInactiveException;
import com.alura.chalenge.application.courses.exceptions.CourseNotFoundExceptionException;
import com.alura.chalenge.application.courses.exceptions.CourseWithCodeNotFoundExceptionException;
import com.alura.chalenge.application.courses.services.CourseServiceImpl;
import com.alura.chalenge.application.enrolls.services.EnrollServiceImpl;
import com.alura.chalenge.application.shared.enums.Status;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.exceptions.UserNotFoundException;
import com.alura.chalenge.application.users.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class EnrollServiceTest {
    @InjectMocks
    EnrollServiceImpl enrollEntityService;

    @Mock
    UserServiceImpl userService;

    @Mock
    CourseServiceImpl courseService;

    @Mock
    EnrollRepository enrollRepository;

    @Test
    void given_enroll_then_createSuccessfully() throws UserNotFoundException, CourseNotFoundExceptionException, CourseInactiveException, CourseWithCodeNotFoundExceptionException {
        Course course = new Course();
        course.setId(1l);

        User student = new User();
        student.setId(1l);

        Mockito.when(enrollRepository.findByStudentAndCourse(any(),any())).thenReturn(Optional.empty());
        Mockito.when(userService.findById(any())).thenReturn(student);
        Mockito.when(courseService.findCourseByCodeAndActive(any())).thenReturn(course);


        assertDoesNotThrow(() -> enrollEntityService.create("test","test"));
    }

    @Test
    void given_enroll_then_createFailedBecauseCourseIsInactive() throws CourseInactiveException, CourseWithCodeNotFoundExceptionException {
        Course course = new Course();
        course.setId(1l);
        course.setCode("ERT");
        course.setStatus(Status.INACTIVE);

        User student = new User();
        student.setId(1l);


        Mockito.when(courseService.findCourseByCodeAndActive(course.getCode())).thenThrow(new CourseInactiveException());
        Mockito.when(enrollRepository.findByStudentAndCourse(any(),any())).thenReturn(Optional.empty());

        Enroll enroll = new Enroll();
        enroll.setStudent(student);
        enroll.setCourse(course);

        assertThrows(
                EntityCreationException.class,
                () -> enrollEntityService.create("test",course.getCode()),
                "Course is inactive"
            );
    }

    @Test
    void given_enroll_then_createFailedBecauseStudentAlreadyEnrolled() {
        Course course = new Course();
        course.setId(1l);
        course.setStatus(Status.INACTIVE);

        User student = new User();
        student.setId(1l);

        Mockito.when(enrollRepository.findByStudentAndCourse(any(),any())).thenReturn(Optional.of(new Enroll()));

        Enroll enroll = new Enroll();
        enroll.setStudent(student);
        enroll.setCourse(course);

        assertThrows(
                EntityCreationException.class,
                () -> enrollEntityService.create("test","test"),
                "Student already enrolled in the course"
        );
    }

}
