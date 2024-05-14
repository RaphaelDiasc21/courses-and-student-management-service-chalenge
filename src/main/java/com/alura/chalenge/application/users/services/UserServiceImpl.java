package com.alura.chalenge.application.users.services;

import com.alura.chalenge.application.shared.enums.Role;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.UserRepository;
import com.alura.chalenge.application.users.exceptions.EmailOrUsernameAlreadyRegisteredException;
import com.alura.chalenge.application.users.exceptions.InstructorNotFoundException;
import com.alura.chalenge.application.users.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) throws EntityCreationException {
        try {
            isUserAlreadyRegistered(user.getEmail(),user.getUsername());
            return userRepository.save(user);
        } catch (EmailOrUsernameAlreadyRegisteredException exception) {
            throw new EntityCreationException(exception.getMessage());
        }
    }

    @Override
    public User findInstructorById(Long id) throws InstructorNotFoundException {
        return userRepository.findByIdAndRoleEquals(id, Role.INSTRUCTOR)
                .orElseThrow(() -> new InstructorNotFoundException(id));
    }

    @Override
    public User findById(Long id) throws UserNotFoundException{
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private User findByEmailOrUsername(String email, String username) {
        return userRepository.findByEmailOrUsername(email,username).
                orElse(null);
    }

    private void isUserAlreadyRegistered(String email, String username) throws EmailOrUsernameAlreadyRegisteredException {
        if(Objects.nonNull(findByEmailOrUsername(email,username))) {
            throw new EmailOrUsernameAlreadyRegisteredException();
        }
    }
}
