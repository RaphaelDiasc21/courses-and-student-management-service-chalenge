package com.alura.chalenge.application.courses;

import com.alura.chalenge.application.courses.exceptions.InactivateCourseException;
import com.alura.chalenge.application.courses.services.CourseServiceImpl;
import com.alura.chalenge.application.shared.enums.Role;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.exceptions.InstructorNotFoundException;
import com.alura.chalenge.application.users.exceptions.UserNotFoundByEmailException;
import com.alura.chalenge.application.users.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class CourseServiceTest {
    @InjectMocks
    CourseServiceImpl courseService;

    @Mock
    CourseRepository courseRepository;

    @Mock
    UserServiceImpl userService;

    @Test
    void given_course_then_createCourseSuccessfully() throws InstructorNotFoundException, UserNotFoundByEmailException {
        User courseInstructor = new User();
        courseInstructor.setId(1l);
        courseInstructor.setRole(Role.INSTRUTOR);
        courseInstructor.setUsername("test");

        Course course = new Course();
        course.setInstructor(courseInstructor);

        Mockito.when(courseRepository.findByCode(any())).thenReturn(Optional.empty());
        Mockito.when(userService.findUserByEmailOrUsername(any())).thenReturn(courseInstructor);

        assertDoesNotThrow(() -> courseService.create(course,courseInstructor.getUsername()));
        Mockito.verify(courseRepository,Mockito.times(1)).save(any());
    }

    @Test
    void given_course_then_createCourseFailedBecauseCodeAlreadyRegistered() {
        User courseInstructor = new User();
        courseInstructor.setId(1l);
        courseInstructor.setRole(Role.INSTRUTOR);
        courseInstructor.setUsername("test");

        Course course = new Course();
        course.setCode("test");
        course.setInstructor(courseInstructor);

        Mockito.when(courseRepository.findByCode(any())).thenReturn(Optional.of(new Course()));

        assertThrows(
                EntityCreationException.class,
                () -> courseService.create(course,courseInstructor.getUsername()),
                "Course with code test already registered\""
        );
    }

    @Test
    void given_course_then_createCourseFailedBecauseIsNotAInstructorBeingUsed() throws UserNotFoundByEmailException {
        User courseInstructor = new User();
        courseInstructor.setId(1l);
        courseInstructor.setRole(Role.ESTUDANTE);
        courseInstructor.setUsername("test");

        Course course = new Course();
        course.setCode("test");
        course.setInstructor(courseInstructor);

        Mockito.when(courseRepository.findByCode(any())).thenReturn(Optional.empty());
        Mockito.when(userService.findUserByEmailOrUsername(any())).thenThrow(UserNotFoundByEmailException.class);

        assertThrows(
                EntityCreationException.class,
                () -> courseService.create(course,courseInstructor.getUsername())
        );
    }

    @Test
    void given_course_then_inactiveCouseFailedBecauseCourseWithCodeNotFound() {
        Mockito.when(courseRepository.findByCode(any())).thenReturn(Optional.empty());
        assertThrows(
                InactivateCourseException.class,
                () -> courseService.inactivateCourse("test")
        );
    }
}
