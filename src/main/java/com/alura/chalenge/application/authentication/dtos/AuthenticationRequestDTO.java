package com.alura.chalenge.application.authentication.dtos;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String username;
    private String password;
}
