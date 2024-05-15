package com.alura.chalenge.application.authentication.services;

import com.alura.chalenge.application.authentication.dtos.AuthenticationRequestDTO;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.exceptions.UserNotFoundByEmailException;
import com.alura.chalenge.application.users.services.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtEncoder jwtEncoder;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserService userService, JwtEncoder jwtEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtEncoder = jwtEncoder;
    }


    public String authenticateUser(AuthenticationRequestDTO authenticationRequest) {
        try {
            User user = userService.findUserByEmailOrUsername(authenticationRequest.getUsername());
            if(!passwordEncoder.matches(authenticationRequest.getPassword(),user.getPassword())) throw new BadCredentialsException("email or password is invalid");

            var now = Instant.now();
            var expiration = 300l;

            var scope = user.getRole();

            var claims = JwtClaimsSet.builder()
                    .issuer("coures-and-users-management-service")
                    .subject(user.getEmail())
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiration))
                    .claim("scope",scope)
                    .build();

            return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        } catch (UserNotFoundByEmailException exception) {
            throw new BadCredentialsException("email/userrname or password is invalid");
        }
    }
}
