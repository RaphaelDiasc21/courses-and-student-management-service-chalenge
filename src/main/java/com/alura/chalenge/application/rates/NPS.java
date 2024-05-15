package com.alura.chalenge.application.rates;

import com.alura.chalenge.application.courses.Course;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NPS {
    private Course course;
    private float score;
    private String nps;
}
