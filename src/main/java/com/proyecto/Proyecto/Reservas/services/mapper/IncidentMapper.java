package com.proyecto.Proyecto.Reservas.services.mapper;

import com.proyecto.Proyecto.Reservas.api.dto.IncidentDtos.*;
import com.proyecto.Proyecto.Reservas.domain.entities.Incident;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IncidentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Incident toEntity(IncidentCreateRequest request);

    IncidentResponse toResponse(Incident incident);

    List<IncidentResponse> toResponseList(List<Incident> incidents);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entityType", ignore = true)
    @Mapping(target = "entityId", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(IncidentUpdateRequest dto, @MappingTarget Incident entity);
}