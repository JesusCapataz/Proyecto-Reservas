package com.proyecto.Proyecto.Reservas.api.dto;

import com.proyecto.Proyecto.Reservas.domain.enums.PaymentMethod;
import com.proyecto.Proyecto.Reservas.domain.enums.TicketStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TicketDtos {

    public record TicketCreateRequest(
            @NotNull(message = "El viaje es requerido")
            Long tripId,

            @NotNull(message = "El pasajero es requerido")
            Long passengerId,

            @NotNull(message = "El número de asiento es requerido")
            @Positive(message = "El número de asiento debe ser positivo")
            Integer seatNumber,

            @NotNull(message = "La parada de origen es requerida")
            Long fromStopId,

            @NotNull(message = "La parada de destino es requerida")
            Long toStopId,

            @NotNull(message = "El precio es requerido")
            @Positive(message = "El precio debe ser positivo")
            BigDecimal price,

            @NotNull(message = "El método de pago es requerido")
            PaymentMethod paymentMethod,

            Long holdId // Opcional, si viene de un hold previo
    ) implements Serializable {}

    public record TicketUpdateRequest(
            TicketStatus status
    ) implements Serializable {}

    public record TicketResponse(
            Long id,
            Long tripId,
            PassengerInfo passenger,
            Integer seatNumber,
            StopInfo fromStop,
            StopInfo toStop,
            BigDecimal price,
            PaymentMethod paymentMethod,
            TicketStatus status,
            String qrCode,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) implements Serializable {}

    public record TicketCancelRequest(
            Long trip_id,
            Long Passenger_id
    ) implements Serializable {}

    // Info simplificada
    public record PassengerInfo(
            Long id,
            String name,
            String email,
            String phone
    ) implements Serializable {}

    public record StopInfo(
            Long id,
            String name
    ) implements Serializable {}
}