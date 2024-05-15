package com.alura.chalenge.application.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserCreateDTO {
    private String name;
    @NotBlank(message = "Field cannot be empty")
    @Pattern(regexp = "^[a-z]+$", message = "username can only have lower case characters without spaces and numerals")
    private String username;
    @Email(message = "is not valid")
    private String email;
    private String password;
    private String role;
}
