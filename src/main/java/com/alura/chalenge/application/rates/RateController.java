package com.alura.chalenge.application.rates;

import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rates")
public class RateController {
    @Autowired
    private RateService rateService;

    @Autowired
    private RateMapper rateMapper;

    @PostMapping
    public ResponseEntity<Rate> create(@RequestBody RateCreateDTO rateCreateDTO) throws EntityCreationException, EntityNotFoundException {
        Rate rate = rateService.create(rateMapper.toEntity(rateCreateDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rate);
    }

    @GetMapping("courses-nps")
    public ResponseEntity<List<NPS>> getCourseNPS() {
        List<NPS> npsList = rateService.getNPSReportPerCourse();
        return ResponseEntity.status(HttpStatus.OK)
                .body(npsList);
    }
}
