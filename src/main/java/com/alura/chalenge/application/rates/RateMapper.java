package com.alura.chalenge.application.rates;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RateMapper {
    @Mapping(source = "dto.studentEmail",target = "student.email")
    @Mapping(source = "dto.courseId",target = "course.id")
    Rate toEntity(RateCreateDTO dto);
}
