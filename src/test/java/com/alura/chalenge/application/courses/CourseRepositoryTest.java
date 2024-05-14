package com.alura.chalenge.application.courses;

import java.time.LocalDate;
import java.util.Date;

import com.alura.chalenge.application.users.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.alura.chalenge.application.shared.enums.Status;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
        course.setCreationDate(LocalDate.now());
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
        course.setCreationDate(LocalDate.now());

        assertThrows(DataIntegrityViolationException.class,
                () -> courseRepository.save(course));
    }

    @Test
    void given_filter_then_searchCoursesPaginated() {
        User user = new User();
        user.setId(1l);

        Course course = new  Course();
        course.setCode("CODE1");
        course.setDescription("Course test description");
        course.setName("Course test");
        course.setStatus(Status.ACTIVE);
        course.setCreationDate(LocalDate.now());
        course.setInstructor(user);

        courseRepository.save(course);


        Course course2 = new  Course();
        course2.setCode("CODE2");
        course2.setDescription("Course test description 2");
        course2.setName("Course test 2");
        course2.setStatus(Status.ACTIVE);
        course2.setCreationDate(LocalDate.now());
        course2.setInstructor(user);

        courseRepository.save(course2);

        Page<Course> page = courseRepository.findByStatus(Status.ACTIVE, PageRequest.of(0,1));
        assertEquals(page.getContent().size(),1);
        assertEquals(page.getContent().get(0).getCode(),"CODE1");
    }
}
