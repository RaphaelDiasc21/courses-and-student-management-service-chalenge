package com.alura.chalenge.application.rates.controllers;

import com.alura.chalenge.application.rates.NPS;
import com.alura.chalenge.application.rates.services.NPSService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("nps")
public class NPSController {


    @Autowired
    private NPSService npsService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("courses-nps")
    public ResponseEntity<List<NPS>> getCourseNPS() {
        List<NPS> npsList = npsService.getNPSReportPerCourse();
        return ResponseEntity.status(HttpStatus.OK)
                .body(npsList);
    }
}
