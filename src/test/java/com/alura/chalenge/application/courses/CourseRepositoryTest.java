package com.alura.chalenge.application.courses;

import java.util.Date;

import com.alura.chalenge.application.users.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.alura.chalenge.application.shared.enums.Status;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    void given_course_then_saveSuccefully() {
        User user = new User();
        user.setId(1l);

        Course course = new  Course();
        course.setCode("CODE1");
        course.setDescription("Course test description");
        course.setName("Course test");
        course.setStatus(Status.ACTIVE);
        course.setCreationDate(new Date());
        course.setInstructor(user);

        Course courseSaved = courseRepository.save(course);
        assertNotNull(courseSaved.getId());
    }


    @Test
    void given_course_then_saveFailedBecauseOfNullValueToPropertiesThatCannotBeNull() {
        User user = new User();
        user.setId(1l);

        Course course = new  Course();
        course.setDescription("Course test description");
        course.setName("Course test");
        course.setStatus(Status.ACTIVE);
        course.setCreationDate(new Date());

        assertThrows(DataIntegrityViolationException.class,
                () -> courseRepository.save(course));
    }
}
