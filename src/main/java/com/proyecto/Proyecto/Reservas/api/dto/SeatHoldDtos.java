package com.proyecto.Proyecto.Reservas.api.dto;

import com.proyecto.Proyecto.Reservas.domain.enums.SeatHoldStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

public class SeatHoldDtos {

    public record SeatHoldCreateRequest(
            @NotNull(message = "El viaje es requerido")
            Long tripId,

            @NotNull(message = "El número de asiento es requerido")
            @Positive(message = "El número de asiento debe ser positivo")
            Integer seatNumber,

            @NotNull(message = "El usuario es requerido")
            Long userId
    ) implements Serializable {}

    public record SeatHoldResponse(
            Long id,
            Long tripId,
            Integer seatNumber,
            Long userId,
            LocalDateTime expiresAt,
            SeatHoldStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) implements Serializable {}

    public record SeatAvailabilityRequest(
            @NotNull(message = "El viaje es requerido")
            Long tripId,

            @NotNull(message = "El número de asiento es requerido")
            Integer seatNumber
    ) implements Serializable {}

    public record SeatAvailabilityResponse(
            Integer seatNumber,
            SeatHoldStatus status
    ) implements Serializable {}
}