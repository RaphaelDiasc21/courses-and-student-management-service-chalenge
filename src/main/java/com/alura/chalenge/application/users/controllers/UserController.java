package com.alura.chalenge.application.users.controllers;

import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.dtos.UserCreateDTO;
import com.alura.chalenge.application.users.dtos.UserResponseDTO;
import com.alura.chalenge.application.users.mappers.UserMapper;
import com.alura.chalenge.application.users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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

    @Operation(
            summary = "Create a user",
            description = "Create a user based on informations required",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The username fields only accepts textual fieds in lowercase, without spaces or special characters")
    )
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserCreateDTO userCreateDTO) throws EntityCreationException, EntityNotFoundException {
        User user = userService.create(userMapper.toEntity(userCreateDTO));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toUserResponseDTO(user));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<UserResponseDTO> searchByUsername(@RequestParam("username") String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.toUserResponseDTO(user));
    }
}
