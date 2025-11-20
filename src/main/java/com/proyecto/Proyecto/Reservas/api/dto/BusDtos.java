package com.proyecto.Proyecto.Reservas.api.dto;

import com.proyecto.Proyecto.Reservas.domain.enums.BusStatus;
import com.proyecto.Proyecto.Reservas.domain.enums.SeatType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

public class BusDtos {

    public record BusCreateRequest(
            @NotBlank(message = "La placa es requerida")
            String plate,

            @NotNull(message = "La capacidad es requerida")
            @Positive(message = "La capacidad debe ser positiva")
            Integer capacity,

            String amenities,

            @Valid
            @NotEmpty(message = "Debe haber al menos un asiento")
            List<SeatCreateRequest> seats
    ) implements Serializable {}

    public record BusUpdateRequest(
            String plate,
            Integer capacity,
            String amenities,
            BusStatus status
    ) implements Serializable {}

    public record BusResponse(
            Long id,
            String plate,
            Integer capacity,
            String amenities,
            BusStatus status,
            List<SeatResponse> seats
    ) implements Serializable {}

    public record SeatCreateRequest(
            @NotNull(message = "El número de asiento es requerido")
            @Positive(message = "El número de asiento debe ser positivo")
            Integer number,

            @NotNull(message = "El tipo de asiento es requerido")
            SeatType type
    ) implements Serializable {}

    public record SeatResponse(
            Long id,
            Integer number,
            SeatType type
    ) implements Serializable {}
}