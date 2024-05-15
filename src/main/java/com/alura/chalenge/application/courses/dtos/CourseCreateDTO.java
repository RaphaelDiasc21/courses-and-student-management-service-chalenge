package com.alura.chalenge.application.courses.dtos;

import com.alura.chalenge.application.shared.enums.Status;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CourseCreateDTO {
    private String name;
    @Pattern(regexp = "^[A-Za-z-]+$", message = "Course code can only have textual characters without spaces, numerals and only dash of special character")
    private String code;
    private String description;
    private Status status;
    private String instructorEmail;
}
