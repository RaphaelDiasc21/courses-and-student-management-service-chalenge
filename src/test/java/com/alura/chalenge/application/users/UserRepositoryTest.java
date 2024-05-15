package com.alura.chalenge.application.users;

import com.alura.chalenge.application.shared.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
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

        assertThrows(DataIntegrityViolationException.class,
                () -> userRepository.save(user));
    }
}
