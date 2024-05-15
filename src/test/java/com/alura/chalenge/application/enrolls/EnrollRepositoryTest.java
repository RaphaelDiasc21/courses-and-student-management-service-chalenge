package com.alura.chalenge.application.enrolls;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.users.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EnrollRepositoryTest {
    @Autowired
    private EnrollRepository enrollRepository;

    @Test
    void given_enroll_then_saveSuccefully() {
        User user = new User();
        user.setId(1l);

        Course course = new Course();
        course.setId(1l);

        Enroll enroll = new Enroll();
        enroll.setCourse(course);
        enroll.setStudent(user);
        enroll.setEnrollDate(LocalDate.now());

        Enroll enrollSaved = enrollRepository.save(enroll);
        assertNotNull(enrollSaved.getId());
    }

    @Test
    void given_enroll_then_saveFailedBecauseOfNullPropertiesThatCannotBeNull() {
        User user = new User();
        user.setId(1l);

        Course course = new Course();
        course.setId(1l);

        Enroll enroll = new Enroll();
        enroll.setEnrollDate(LocalDate.now());

        assertThrows(DataIntegrityViolationException.class,
                () -> enrollRepository.save(enroll));
    }
}
