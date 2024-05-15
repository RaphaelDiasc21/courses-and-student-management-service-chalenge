package com.alura.chalenge.application.courses.controllers;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.dtos.CourseCreateDTO;
import com.alura.chalenge.application.courses.exceptions.InactivateCourseException;
import com.alura.chalenge.application.courses.mappers.CourseMapper;
import com.alura.chalenge.application.courses.services.CourseService;
import com.alura.chalenge.application.shared.constants.PaginationConstants;
import com.alura.chalenge.application.shared.enums.Status;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper courseMapper;

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody @Valid CourseCreateDTO courseCreateDTO) throws EntityCreationException, EntityNotFoundException {
        Course course = courseService.create(courseMapper.toEntity(courseCreateDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    public ResponseEntity<Void> inactivateCourse(@RequestParam("code") String code) throws InactivateCourseException {
        courseService.inactivateCourse(code);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    @Parameters({
            @Parameter(name = "page", example = "0"),
            @Parameter(name = "size", example = "5")
    })
    public ResponseEntity<Page<Course>> search(@RequestParam(required = false) Status status,
                                               @PageableDefault(sort = "id",page = PaginationConstants.DEFAULT_PAGE, size = PaginationConstants.DEFAULT_PAGE_SIZE, direction = Sort.Direction.ASC) @Parameter(hidden = true) Pageable pageable) {
        Page<Course> coursesPage = courseService.search(status,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(coursesPage);
    }
}
