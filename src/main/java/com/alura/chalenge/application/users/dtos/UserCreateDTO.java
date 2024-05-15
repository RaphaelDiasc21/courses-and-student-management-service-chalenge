package com.alura.chalenge.application.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserCreateDTO {
    @NotEmpty(message = "Field name cannot be null or empty")
    private String name;

    @NotEmpty(message = "Field name cannot be null or empty")
    @Pattern(regexp = "^[a-z]+$", message = "username can only have lower case characters without spaces and numerals")
    private String username;

    @Email(message = "is not valid")
    private String email;

    @NotEmpty(message = "Field password cannot be null or empty")
    private String password;

    @Pattern(regexp = "^(INSTRUTOR|ESTUDANTE|ADMIN)$",message = "Role field must be INSTRUTOR or ESTUDANTE or ADMIN")
    private String role;
}
