package com.proyecto.Proyecto.Reservas.services.mapper;


import com.proyecto.Proyecto.Reservas.api.dto.BusDtos.*;
import com.proyecto.Proyecto.Reservas.domain.entities.Bus;
import com.proyecto.Proyecto.Reservas.domain.entities.Seat;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BusMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "seats", ignore = true)
    Bus toEntity(BusCreateRequest request);

    @Mapping(target = "seats", source = "seats")
    BusResponse toResponse(Bus bus);

    List<BusResponse> toResponseList(List<Bus> buses);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bus", ignore = true)
    Seat toSeatEntity(SeatCreateRequest request);

    SeatResponse toSeatResponse(Seat seat);

    List<SeatResponse> toSeatResponseList(List<Seat> seats);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seats", ignore = true)
    void updateEntityFromDto(BusUpdateRequest dto, @MappingTarget Bus entity);
}



