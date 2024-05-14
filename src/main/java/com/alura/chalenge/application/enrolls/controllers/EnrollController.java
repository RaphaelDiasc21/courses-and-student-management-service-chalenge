package com.alura.chalenge.application.enrolls.controllers;

import com.alura.chalenge.application.enrolls.Enroll;
import com.alura.chalenge.application.enrolls.dtos.EnrollCreateDTO;
import com.alura.chalenge.application.enrolls.mappers.EnrollMapper;
import com.alura.chalenge.application.enrolls.services.EnrollService;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
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

    @PostMapping
    public ResponseEntity<Enroll> create(@RequestBody EnrollCreateDTO enrollCreateDTO) throws EntityCreationException, EntityNotFoundException {
        Enroll enroll = enrollService.create(enrollMapper.toEntity(enrollCreateDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(enroll);
    }
}
