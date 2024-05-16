package com.alura.chalenge.application.rates.mappers;

import com.alura.chalenge.application.rates.Rate;
import com.alura.chalenge.application.rates.dtos.RateCreateDTO;
import com.alura.chalenge.application.rates.dtos.RateResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RateMapper {

    @Mapping(source = "rate.student.username",target = "studentUsername")
    @Mapping(source = "rate.student.email",target = "studentEmail")
    @Mapping(source = "rate.course.code", target = "courseCode")
    RateResponseDTO toResponseDTO(Rate rate);
}
