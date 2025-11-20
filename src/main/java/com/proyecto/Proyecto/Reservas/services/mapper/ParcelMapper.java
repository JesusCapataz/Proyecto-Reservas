package com.proyecto.Proyecto.Reservas.services.mapper;


import com.proyecto.Proyecto.Reservas.api.dto.ParcelDtos.*;
import com.proyecto.Proyecto.Reservas.domain.entities.Parcel;
import com.proyecto.Proyecto.Reservas.domain.entities.Stop;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParcelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "fromStop", ignore = true)
    @Mapping(target = "toStop", ignore = true)
    @Mapping(target = "trip", ignore = true)
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "proofPhotoUrl", ignore = true)
    @Mapping(target = "deliveryOtp", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Parcel toEntity(ParcelCreateRequest request);

    @Mapping(target = "fromStop", source = "fromStop")
    @Mapping(target = "toStop", source = "toStop")
    @Mapping(target = "tripId", source = "trip.id")
    ParcelResponse toResponse(Parcel parcel);

    List<ParcelResponse> toResponseList(List<Parcel> parcels);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    StopInfo toStopInfo(Stop stop);

    @Mapping(target = "code", source = "code")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "senderName", source = "senderName")
    @Mapping(target = "receiverName", source = "receiverName")
    @Mapping(target = "fromStopName", source = "fromStop.name")
    @Mapping(target = "toStopName", source = "toStop.name")
    ParcelTrackingResponse toTrackingResponse(Parcel parcel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "senderName", ignore = true)
    @Mapping(target = "senderPhone", ignore = true)
    @Mapping(target = "receiverName", ignore = true)
    @Mapping(target = "receiverPhone", ignore = true)
    @Mapping(target = "fromStop", ignore = true)
    @Mapping(target = "toStop", ignore = true)
    @Mapping(target = "trip", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "deliveryOtp", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(ParcelUpdateRequest dto, @MappingTarget Parcel entity);
}



