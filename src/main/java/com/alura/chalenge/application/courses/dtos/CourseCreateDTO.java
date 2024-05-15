package com.alura.chalenge.application.courses.dtos;

import com.alura.chalenge.application.courses.validators.StatusEnumValidator;
import com.alura.chalenge.application.shared.enums.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CourseCreateDTO {
    @NotEmpty(message = "Name field cannot be null or empty")
    private String name;

    @NotEmpty(message = "Code field cannot be null or empty")
    @Pattern(regexp = "^[A-Za-z-]+$", message = "Course code can only have textual characters without spaces, numerals and only dash of special character")
    private String code;

    private String description;

    @Pattern(regexp = "^(ACTIVE|INACTIVE)$",message = "Status field must be ACTIVE or INACTIVE")
    private String status;

    @NotEmpty(message = "Instructor cannot be null or empty")
    private String instructorIdentification;
}
