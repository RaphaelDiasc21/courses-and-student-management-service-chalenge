package com.alura.chalenge.application.users.services;

import com.alura.chalenge.application.shared.enums.Role;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.UserRepository;
import com.alura.chalenge.application.users.exceptions.EmailOrUsernameAlreadyRegisteredException;
import com.alura.chalenge.application.users.exceptions.InstructorNotFoundException;
import com.alura.chalenge.application.users.exceptions.UserNotFoundByEmailException;
import com.alura.chalenge.application.users.exceptions.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) throws EntityCreationException {
        try {
            isUserAlreadyRegistered(user.getEmail(),user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (EmailOrUsernameAlreadyRegisteredException exception) {
            throw new EntityCreationException(exception.getMessage());
        }
    }

    @Override
    public User findInstructorByEmail(String email) throws InstructorNotFoundException {
        return userRepository.findByEmailAndRoleEquals(email, Role.INSTRUCTOR)
                .orElseThrow(() -> new InstructorNotFoundException(email));
    }

    @Override
    public User findById(Long id) throws UserNotFoundException{
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    @Override
    public User findUserByEmailOrUsername(String username) throws UserNotFoundByEmailException {
        return userRepository
                .searchUserByEmailOrUsername(username)
                .orElseThrow(() -> new UserNotFoundByEmailException(username));
    }



    private User findByEmailOrUsername(String email, String username) {
        return userRepository.findUserByEmailOrUsername(email,username).
                orElse(null);
    }

    private void isUserAlreadyRegistered(String email, String username) throws EmailOrUsernameAlreadyRegisteredException {
        if(Objects.nonNull(findByEmailOrUsername(email,username))) {
            throw new EmailOrUsernameAlreadyRegisteredException();
        }
    }
}
