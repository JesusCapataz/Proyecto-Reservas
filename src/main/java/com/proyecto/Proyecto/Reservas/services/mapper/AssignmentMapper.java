package com.proyecto.Proyecto.Reservas.services.mapper;


import com.proyecto.Proyecto.Reservas.api.dto.AssignmentDtos.*;
import com.proyecto.Proyecto.Reservas.domain.entities.Assignment;
import com.proyecto.Proyecto.Reservas.domain.entities.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trip", ignore = true)
    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "dispatcher", ignore = true)
    @Mapping(target = "assignedAt", ignore = true)
    Assignment toEntity(AssignmentCreateRequest request);

    @Mapping(target = "tripId", source = "trip.id")
    @Mapping(target = "driver", source = "driver")
    @Mapping(target = "dispatcher", source = "dispatcher")
    AssignmentResponse toResponse(Assignment assignment);

    List<AssignmentResponse> toResponseList(List<Assignment> assignments);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "phone", source = "phone")
    UserInfo toUserInfo(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trip", ignore = true)
    @Mapping(target = "dispatcher", ignore = true)
    @Mapping(target = "assignedAt", ignore = true)
    @Mapping(target = "driver", ignore = true)
    void updateEntityFromDto(AssignmentUpdateRequest dto, @MappingTarget Assignment entity);
}



