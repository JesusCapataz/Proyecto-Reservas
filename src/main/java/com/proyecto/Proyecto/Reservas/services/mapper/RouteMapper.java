package com.proyecto.Proyecto.Reservas.services.mapper;


import com.proyecto.Proyecto.Reservas.api.dto.RouteDtos.*;
import com.proyecto.Proyecto.Reservas.domain.entities.Route;
import com.proyecto.Proyecto.Reservas.domain.entities.Stop;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stops", ignore = true)
    Route toEntity(RouteCreateRequest request);

    @Mapping(target = "stops", source = "stops")
    RouteResponse toResponse(Route route);

    List<RouteResponse> toResponseList(List<Route> routes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "route", ignore = true)
    Stop toStopEntity(StopCreateRequest request);

    StopResponse toStopResponse(Stop stop);

    List<StopResponse> toStopResponseList(List<Stop> stops);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "origin", ignore = true)
    @Mapping(target = "destination", ignore = true)
    @Mapping(target = "stops", ignore = true)
    void updateEntityFromDto(RouteUpdateRequest dto, @MappingTarget Route entity);
}



