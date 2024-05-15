package com.alura.chalenge.application.courses.mappers;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.courses.dtos.CourseCreateDTO;
import com.alura.chalenge.application.courses.dtos.CourseResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course toEntity(CourseCreateDTO dto);

    @Mapping(source = "course.instructor.name",target = "instructor.name")
    @Mapping(source = "course.instructor.email",target = "instructor.email")
    CourseResponseDTO toResponseDTO(Course course);
}
