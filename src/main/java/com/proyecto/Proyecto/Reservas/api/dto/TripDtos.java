package com.proyecto.Proyecto.Reservas.api.dto;

import com.proyecto.Proyecto.Reservas.domain.enums.TripStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TripDtos {

    public record TripCreateRequest(
            @NotNull(message = "La ruta es requerida")
            Long routeId,

            @NotNull(message = "El bus es requerido")
            Long busId,

            @NotNull(message = "La fecha es requerida")
            LocalDate date,

            @NotNull(message = "La hora de salida es requerida")
            LocalDateTime departureAt,

            @NotNull(message = "La hora estimada de llegada es requerida")
            LocalDateTime arrivalEta
    ) implements Serializable {}

    public record TripUpdateRequest(
            LocalDateTime departureAt,
            LocalDateTime arrivalEta,
            TripStatus status
    ) implements Serializable {}

    public record TripResponse(
            Long id,
            RouteInfo route,
            BusInfo bus,
            LocalDate date,
            LocalDateTime departureAt,
            LocalDateTime arrivalEta,
            TripStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) implements Serializable {}

    public record TripSearchRequest(
            Long routeId,
            LocalDate date,
            TripStatus status
    ) implements Serializable {}

    // Info simplificada para evitar referencias circulares
    public record RouteInfo(
            Long id,
            String code,
            String name,
            String origin,
            String destination
    ) implements Serializable {}

    public record BusInfo(
            Long id,
            String plate,
            Integer capacity
    ) implements Serializable {}
}