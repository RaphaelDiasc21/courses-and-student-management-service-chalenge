package com.alura.chalenge.application.rates.controllers;

import com.alura.chalenge.application.rates.*;
import com.alura.chalenge.application.rates.dtos.RateCreateDTO;
import com.alura.chalenge.application.rates.Rate;
import com.alura.chalenge.application.rates.dtos.RateResponseDTO;
import com.alura.chalenge.application.rates.mappers.RateMapper;
import com.alura.chalenge.application.rates.services.RateService;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rates")
public class RateController {
    @Autowired
    private RateService rateService;

    @Autowired
    private RateMapper rateMapper;

    @Operation(
            summary = "Create a rate",
            description = "Create an rate based on informations required, getting the user info from token authenticated",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The rate field is the score of the rate")
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<RateResponseDTO> create(@RequestBody RateCreateDTO rateCreateDTO) throws EntityCreationException, EntityNotFoundException {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long studentId = Long.parseLong(principal.getClaims().get("sub").toString());

        Rate rate = rateService.create(studentId,rateCreateDTO.getCourseCode(),rateCreateDTO.getRate());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rateMapper.toResponseDTO(rate));
    }
}
