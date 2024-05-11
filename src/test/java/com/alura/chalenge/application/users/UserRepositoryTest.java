package com.alura.chalenge.application.users;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.enrolls.Enroll;
import com.alura.chalenge.application.enrolls.EnrollRepository;
import com.alura.chalenge.application.shared.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void given_user_then_saveSuccefully() {
        User user = new User();
        user.setId(1l);
        user.setEmail("test@test.com");
        user.setName("test");
        user.setUsername("test");
        user.setPassword("test");
        user.setRole(Role.ADMIN);
        user.setCreationDate(new Date());

        User userSaved = userRepository.save(user);
        assertNotNull(userSaved.getId());
    }

    @Test
    void given_enroll_then_saveFailedBecauseOfNullPropertiesThatCannotBeNull() {
        User user = new User();
        user.setId(1l);
        user.setEmail("test@test.com");
        user.setName("test");
        user.setUsername("test");
        user.setCreationDate(new Date());

        assertThrows(DataIntegrityViolationException.class,
                () -> userRepository.save(user));
    }
}
