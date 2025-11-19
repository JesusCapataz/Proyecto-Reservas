package com.proyecto.Proyecto.Reservas.api.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class BaggageDtos {

    public record BaggageCreateRequest(
            @NotNull(message = "El ticket es requerido")
            Long ticketId,

            @NotNull(message = "El peso es requerido")
            @Positive(message = "El peso debe ser positivo")
            Double weightKg,

            @NotNull(message = "La tarifa es requerida")
            @PositiveOrZero(message = "La tarifa no puede ser negativa")
            BigDecimal fee,

            @NotNull(message= "La etiqueta de codigo es requerida")
            String tagCode
    ) implements Serializable {}

    public record BaggageUpdateRequest(
            Double weightKg,
            BigDecimal fee,
            String tagCode
    ) implements Serializable {}

    public record BaggageResponse(
            Long id,
            Long ticketId,
            Double weightKg,
            BigDecimal fee,
            String tagCode
    ) implements Serializable {}
}