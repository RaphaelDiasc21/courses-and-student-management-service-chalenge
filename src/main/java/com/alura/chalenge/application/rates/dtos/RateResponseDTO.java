package com.alura.chalenge.application.rates.dtos;

import lombok.Data;

@Data
public class RateResponseDTO {
    private String studentUsername;
    private String studentEmail;
    private String courseCode;
    private Double rate;
}
