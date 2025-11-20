package com.proyecto.Proyecto.Reservas.services.mapper;

import com.proyecto.Proyecto.Reservas.api.dto.UserDtos.*;
import com.proyecto.Proyecto.Reservas.domain.entities.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", constant = "true")
    User toEntity(UserCreateRequest request);

    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateEntityFromDto(UserUpdateRequest dto, @MappingTarget User entity);
}
