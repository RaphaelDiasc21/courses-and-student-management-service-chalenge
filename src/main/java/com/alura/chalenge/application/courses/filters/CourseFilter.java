package com.alura.chalenge.application.courses.filters;

import com.alura.chalenge.application.shared.enums.Status;
import lombok.Data;

@Data
public class CourseFilter {
    private Status status;
}
