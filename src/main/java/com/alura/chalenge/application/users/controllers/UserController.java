package com.alura.chalenge.application.users.controllers;

import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.dtos.UserCreateDTO;
import com.alura.chalenge.application.users.dtos.UserResponseDTO;
import com.alura.chalenge.application.users.mappers.UserMapper;
import com.alura.chalenge.application.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserCreateDTO userCreateDTO) throws EntityCreationException, EntityNotFoundException {
        User user = userService.create(userMapper.toEntity(userCreateDTO));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toUserResponseDTO(user));
    }

    @GetMapping
    public ResponseEntity<UserResponseDTO> searchByUsername(@RequestParam("username") String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.toUserResponseDTO(user));
    }
}
