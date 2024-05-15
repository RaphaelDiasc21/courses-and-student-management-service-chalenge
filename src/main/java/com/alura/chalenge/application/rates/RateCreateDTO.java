package com.alura.chalenge.application.rates;

import lombok.Data;

@Data
public class RateCreateDTO {
    private String studentEmail;
    private Long courseId;
    private Double rate;
}
