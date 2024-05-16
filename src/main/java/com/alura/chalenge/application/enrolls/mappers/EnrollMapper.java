package com.alura.chalenge.application.enrolls.mappers;

import com.alura.chalenge.application.enrolls.Enroll;
import com.alura.chalenge.application.enrolls.dtos.EnrollResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollMapper {

    @Mapping(source = "enroll.course.code",target = "courseCode")
    @Mapping(source = "enroll.course.name",target = "courseName")
    @Mapping(source = "enroll.student.username",target = "studentUsername")
    @Mapping(source = "enroll.student.email",target = "studentEmail")
    EnrollResponseDTO toResponseDTO(Enroll enroll);
}
