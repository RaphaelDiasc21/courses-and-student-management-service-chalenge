package com.alura.chalenge.application.users.dtos;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String name;
    private String email;
    private String role;
}
