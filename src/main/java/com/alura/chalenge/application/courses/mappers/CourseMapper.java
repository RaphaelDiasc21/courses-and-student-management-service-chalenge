package com.alura.chalenge.application.courses.mappers;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.dtos.CourseCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(source = "dto.instructorEmail",target = "instructor.email")
    Course toEntity(CourseCreateDTO dto);
}
