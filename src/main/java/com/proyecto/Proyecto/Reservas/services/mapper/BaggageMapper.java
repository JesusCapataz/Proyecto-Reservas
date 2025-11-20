package com.proyecto.Proyecto.Reservas.services.mapper;
import com.proyecto.Proyecto.Reservas.api.dto.BaggageDtos.*;
import com.proyecto.Proyecto.Reservas.domain.entities.Baggage;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BaggageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticket", ignore = true)
    Baggage toEntity(BaggageCreateRequest request);

    @Mapping(target = "ticketId", source = "ticket.id")
    BaggageResponse toResponse(Baggage baggage);

    List<BaggageResponse> toResponseList(List<Baggage> baggages);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticket", ignore = true)
    void updateEntityFromDto(BaggageUpdateRequest dto, @MappingTarget Baggage entity);
}