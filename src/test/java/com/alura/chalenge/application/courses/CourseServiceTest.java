package com.alura.chalenge.application.courses;

import com.alura.chalenge.application.courses.services.CourseServiceImpl;
import com.alura.chalenge.application.shared.enums.Role;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.exceptions.InstructorNotFoundException;
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
    void given_course_then_createCourseSuccessfully() throws InstructorNotFoundException {
        User courseInstructor = new User();
        courseInstructor.setId(1l);
        courseInstructor.setRole(Role.INSTRUCTOR);

        Course course = new Course();
        course.setInstructor(courseInstructor);

        Mockito.when(courseRepository.findByCode(any())).thenReturn(Optional.empty());
        Mockito.when(userService.findInstructorById(any())).thenReturn(courseInstructor);

        assertDoesNotThrow(() -> courseService.create(course));
        Mockito.verify(courseRepository,Mockito.times(1)).save(any());
    }

    @Test
    void given_course_then_createCourseFailedBecauseCodeAlreadyRegistered() {
        User courseInstructor = new User();
        courseInstructor.setId(1l);
        courseInstructor.setRole(Role.INSTRUCTOR);

        Course course = new Course();
        course.setCode("test");
        course.setInstructor(courseInstructor);

        Mockito.when(courseRepository.findByCode(any())).thenReturn(Optional.of(new Course()));

        assertThrows(
                EntityCreationException.class,
                () -> courseService.create(course),
                "Course with code test already registered\""
        );
    }

    @Test
    void given_course_then_createCourseFailedBecauseIsNotAInstructorBeingUsed() throws InstructorNotFoundException {
        User courseInstructor = new User();
        courseInstructor.setId(1l);
        courseInstructor.setRole(Role.STUDENT);

        Course course = new Course();
        course.setCode("test");
        course.setInstructor(courseInstructor);

        Mockito.when(courseRepository.findByCode(any())).thenReturn(Optional.empty());
        Mockito.when(userService.findInstructorById(any())).thenThrow(InstructorNotFoundException.class);

        assertThrows(
                EntityCreationException.class,
                () -> courseService.create(course)
        );
    }
}
