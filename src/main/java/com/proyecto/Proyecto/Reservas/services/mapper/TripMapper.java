package com.proyecto.Proyecto.Reservas.services.mapper;


import com.proyecto.Proyecto.Reservas.api.dto.TripDtos.*;
import com.proyecto.Proyecto.Reservas.domain.entities.Bus;
import com.proyecto.Proyecto.Reservas.domain.entities.Route;
import com.proyecto.Proyecto.Reservas.domain.entities.Trip;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "bus", ignore = true)
    @Mapping(target = "status", constant = "SCHEDULED")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Trip toEntity(TripCreateRequest request);

    @Mapping(target = "route", source = "route")
    @Mapping(target = "bus", source = "bus")
    TripResponse toResponse(Trip trip);

    List<TripResponse> toResponseList(List<Trip> trips);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "origin", source = "origin")
    @Mapping(target = "destination", source = "destination")
    RouteInfo toRouteInfo(Route route);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "plate", source = "plate")
    @Mapping(target = "capacity", source = "capacity")
    BusInfo toBusInfo(Bus bus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "bus", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(TripUpdateRequest dto, @MappingTarget Trip entity);
}




