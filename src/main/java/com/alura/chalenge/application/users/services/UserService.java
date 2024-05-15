package com.alura.chalenge.application.users.services;

import com.alura.chalenge.application.shared.interfaces.EntityService;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.exceptions.InstructorNotFoundException;
import com.alura.chalenge.application.users.exceptions.UserNotFoundByEmailException;
import com.alura.chalenge.application.users.exceptions.UserNotFoundException;


public interface UserService extends EntityService<User> {
    User findInstructorByEmail(String email) throws InstructorNotFoundException;
    User findById(Long id) throws UserNotFoundException;
    User findByUsername(String username);
    User findUserByEmailOrUsername(String username) throws UserNotFoundByEmailException;
}
