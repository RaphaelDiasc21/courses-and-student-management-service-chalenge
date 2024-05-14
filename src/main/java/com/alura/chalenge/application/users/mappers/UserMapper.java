package com.alura.chalenge.application.users.mappers;

import ch.qos.logback.core.model.ComponentModel;
import com.alura.chalenge.application.users.User;
import com.alura.chalenge.application.users.dtos.UserCreateDTO;
import com.alura.chalenge.application.users.dtos.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserCreateDTO userCreateDTO);
    UserResponseDTO toUserResponseDTO(User user);
}
