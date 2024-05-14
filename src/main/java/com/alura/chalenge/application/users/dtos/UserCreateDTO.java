package com.alura.chalenge.application.users.dtos;

import lombok.Data;

@Data
public class UserCreateDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private String role;
}
