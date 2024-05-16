package com.alura.chalenge.application.enrolls.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EnrollCreateDTO {
    @NotEmpty(message = "Student cannot be null or empty")
    String studentIdentification;

    @NotEmpty(message = "Course cannot be null or empty")
    String courseCode;
}
