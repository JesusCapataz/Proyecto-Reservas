package com.proyecto.Proyecto.Reservas.api.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class FareRuleDtos {

    public record FareRuleCreateRequest(
            @NotNull(message = "La ruta es requerida")
            Long routeId,

            @NotNull(message = "La parada de origen es requerida")
            Long fromStopId,

            @NotNull(message = "La parada de destino es requerida")
            Long toStopId,

            @NotNull(message = "El precio base es requerido")
            @Positive(message = "El precio base debe ser positivo")
            BigDecimal basePrice,

            String discounts, // JSON string con descuentos

            Boolean dynamicPricing
    ) implements Serializable {}

    public record FareRuleUpdateRequest(
            BigDecimal basePrice,
            String discounts,
            Boolean dynamicPricing
    ) implements Serializable {}

    public record FareRuleResponse(
            Long id,
            Long routeId,
            StopInfo fromStop,
            StopInfo toStop,
            BigDecimal basePrice,
            String discounts,
            Boolean dynamicPricing
    ) implements Serializable {}

    public record FareCalculationRequest(
            @NotNull(message = "La ruta es requerida")
            Long routeId,

            @NotNull(message = "La parada de origen es requerida")
            Long fromStopId,

            @NotNull(message = "La parada de destino es requerida")
            Long toStopId,

            String passengerType
    ) implements Serializable {}

    public record FareCalculationResponse(
            BigDecimal basePrice,
            BigDecimal finalPrice,
            String discountApplied
    ) implements Serializable {}

    // Info simplificada
    public record StopInfo(
            Long id,
            String name
    ) implements Serializable {}
}