package com.alura.chalenge.application.enrolls.mappers;

import com.alura.chalenge.application.enrolls.Enroll;
import com.alura.chalenge.application.enrolls.dtos.EnrollCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollMapper {

    @Mapping(source = "dto.studentEmail",target = "student.email")
    @Mapping(source = "dto.courseId",target = "course.id")
    Enroll toEntity(EnrollCreateDTO dto);
}
