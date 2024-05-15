package com.alura.chalenge.application.enrolls.dtos;

import lombok.Data;

@Data
public class EnrollResponseDTO {
    private String courseCode;
    private String courseName;
    private String studentUsername;
    private String studentEmail;
}
