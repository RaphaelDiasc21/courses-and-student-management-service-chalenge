package com.alura.chalenge.application.authentication.controllers;

import com.alura.chalenge.application.authentication.dtos.AuthenticationRequestDTO;
import com.alura.chalenge.application.authentication.dtos.AuthenticationResponseDTO;
import com.alura.chalenge.application.authentication.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticateUser(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        String token = authenticationService.authenticateUser(authenticationRequestDTO);
        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
        authenticationResponseDTO.setAccessToken(token);

        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponseDTO);
    }
}
