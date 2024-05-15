package com.alura.chalenge.application.rates.dtos;

import lombok.Data;

@Data
public class RateCreateDTO {
    private String courseCode;
    private Double rate;
}
