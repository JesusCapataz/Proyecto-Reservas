package com.proyecto.Proyecto.Reservas.services.mapper;

import com.proyecto.Proyecto.Reservas.api.dto.SeatHoldDtos.*;
import com.proyecto.Proyecto.Reservas.domain.entities.SeatHold;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatHoldMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trip", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    @Mapping(target = "status", constant = "HOLD")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SeatHold toEntity(SeatHoldCreateRequest request);

    @Mapping(target = "tripId", source = "trip.id")
    @Mapping(target = "userId", source = "user.id")
    SeatHoldResponse toResponse(SeatHold seatHold);

    List<SeatHoldResponse> toResponseList(List<SeatHold> seatHolds);
}