package com.alura.chalenge.application.courses.dtos;

import com.alura.chalenge.application.shared.enums.Status;
import lombok.Data;

@Data
public class CourseCreateDTO {
    private String name;
    private String code;
    private String description;
    private Status status;
    private String instructorEmail;
}
