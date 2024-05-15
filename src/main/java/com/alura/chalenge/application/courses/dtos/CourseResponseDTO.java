package com.alura.chalenge.application.courses.dtos;

import lombok.Data;

@Data
public class CourseResponseDTO {
    private String name;
    private String code;
    private String description;
    private String status;
    private CourseInstructorResponseDTO instructor;
}
