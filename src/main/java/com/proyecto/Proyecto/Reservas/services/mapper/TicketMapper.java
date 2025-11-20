package com.proyecto.Proyecto.Reservas.services.mapper;


import com.proyecto.Proyecto.Reservas.api.dto.TicketDtos.*;
import com.proyecto.Proyecto.Reservas.domain.entities.Stop;
import com.proyecto.Proyecto.Reservas.domain.entities.Ticket;
import com.proyecto.Proyecto.Reservas.domain.entities.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trip", ignore = true)
    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "fromStop", ignore = true)
    @Mapping(target = "toStop", ignore = true)
    @Mapping(target = "qrCode", ignore = true)
    @Mapping(target = "status", constant = "SOLD")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Ticket toEntity(TicketCreateRequest request);

    @Mapping(target = "tripId", source = "trip.id")
    @Mapping(target = "passenger", source = "passenger")
    @Mapping(target = "fromStop", source = "fromStop")
    @Mapping(target = "toStop", source = "toStop")
    TicketResponse toResponse(Ticket ticket);

    List<TicketResponse> toResponseList(List<Ticket> tickets);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    PassengerInfo toPassengerInfo(User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    StopInfo toStopInfo(Stop stop);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trip", ignore = true)
    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "fromStop", ignore = true)
    @Mapping(target = "toStop", ignore = true)
    @Mapping(target = "seatNumber", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "paymentMethod", ignore = true)
    @Mapping(target = "qrCode", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(TicketUpdateRequest dto, @MappingTarget Ticket entity);
}



