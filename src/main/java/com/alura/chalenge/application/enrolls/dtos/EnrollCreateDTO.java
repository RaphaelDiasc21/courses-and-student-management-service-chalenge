package com.alura.chalenge.application.enrolls.dtos;

import lombok.Data;

@Data
public class EnrollCreateDTO {
    String studentEmail;
    Long courseId;
}
