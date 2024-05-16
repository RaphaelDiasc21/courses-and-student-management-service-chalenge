package com.alura.chalenge.application.enrolls.controllers;

import com.alura.chalenge.application.enrolls.Enroll;
import com.alura.chalenge.application.enrolls.dtos.EnrollCreateDTO;

import com.alura.chalenge.application.enrolls.dtos.EnrollResponseDTO;
import com.alura.chalenge.application.enrolls.mappers.EnrollMapper;
import com.alura.chalenge.application.enrolls.services.EnrollService;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enrolls")
public class EnrollController {
    @Autowired
    private EnrollService enrollService;

    @Autowired
    private EnrollMapper enrollMapper;

    @Operation(
            summary = "Create a enroll",
            description = "Create an enroll based on informations required",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The studentIdentification expects the student email or student username")
    )
    @PostMapping
    public ResponseEntity<EnrollResponseDTO> create(@RequestBody @Valid EnrollCreateDTO enrollCreateDTO) throws EntityCreationException, EntityNotFoundException {
        Enroll enroll = enrollService.create(enrollCreateDTO.getStudentIdentification(),enrollCreateDTO.getCourseCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollMapper.toResponseDTO(enroll));
    }
}
