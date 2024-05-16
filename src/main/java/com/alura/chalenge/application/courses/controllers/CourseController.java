package com.alura.chalenge.application.courses.controllers;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.dtos.CourseCreateDTO;
import com.alura.chalenge.application.courses.dtos.CourseResponseDTO;
import com.alura.chalenge.application.courses.exceptions.InactivateCourseException;
import com.alura.chalenge.application.courses.mappers.CourseMapper;
import com.alura.chalenge.application.courses.services.CourseService;
import com.alura.chalenge.application.shared.constants.PaginationConstants;
import com.alura.chalenge.application.shared.enums.Status;
import com.alura.chalenge.application.shared.exceptions.EntityCreationException;
import com.alura.chalenge.application.shared.exceptions.EntityNotFoundException;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper courseMapper;

    @Operation(
            summary = "Create a course",
            description = "Create a course based on informations required",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The instructorIdentification field, expects the instructor email or the instructor username, also the code fields only accepts textual fieds without spaces or special characters")
    )
    @PostMapping
    public ResponseEntity<CourseResponseDTO> create(@RequestBody @Valid CourseCreateDTO courseCreateDTO) throws EntityCreationException, EntityNotFoundException {
        Course course = courseService.create(
                courseMapper.toEntity(courseCreateDTO),
                courseCreateDTO.getInstructorIdentification()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(courseMapper.toResponseDTO(course));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{code}")
    public ResponseEntity<Void> inactivateCourse(@PathVariable("code") String code) throws InactivateCourseException {
        courseService.inactivateCourse(code);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    @Parameters({
            @Parameter(name = "page", example = "0"),
            @Parameter(name = "size", example = "5")
    })
    public ResponseEntity<Page<CourseResponseDTO>> search(@RequestParam(required = false) Status status,
                                               @PageableDefault(sort = "id",page = PaginationConstants.DEFAULT_PAGE, size = PaginationConstants.DEFAULT_PAGE_SIZE, direction = Sort.Direction.ASC) @Parameter(hidden = true) Pageable pageable) {
        Page<Course> coursesPage = courseService.search(status,pageable);

        PageImpl<CourseResponseDTO> pageResponse = new PageImpl<>(mapCoursesPageContentToDTO(coursesPage.getContent()),pageable,coursesPage.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
    }
    
    private List<CourseResponseDTO> mapCoursesPageContentToDTO(List<Course> courseList) {
        return courseList.stream()
                .map(course -> courseMapper.toResponseDTO(course))
                .collect(Collectors.toList());
    }
}
