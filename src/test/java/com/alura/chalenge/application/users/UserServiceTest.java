package com.alura.chalenge.application.users;

import com.alura.chalenge.application.shared.enums.Role;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.users.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Test
    void given_user_then_createFailedBecauseEmailOrUsernameAlreadyRegistered() {
        Mockito.when(userRepository.findUserByEmailOrUsername(any(),any())).thenReturn(Optional.of(new User()));

        User user = new User();
        user.setName("test name");
        user.setUsername("test username");
        user.setPassword("test");
        user.setEmail("test@test.com");
        user.setRole(Role.ADMIN);

        assertThrows(EntityCreationException.class,
                () -> userService.create(user),
                "Email or username already registered"
        );
    }
}
