package com.proyecto.Proyecto.Reservas.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

public class RouteDtos {

    public record RouteCreateRequest(
            @NotBlank(message = "El código es requerido")
            String code,

            @NotBlank(message = "El nombre es requerido")
            String name,

            @NotBlank(message = "El origen es requerido")
            String origin,

            @NotBlank(message = "El destino es requerido")
            String destination,

            @NotNull(message = "La distancia es requerida")
            @Positive(message = "La distancia debe ser positiva")
            Double distanceKm,

            @NotNull(message = "La duración es requerida")
            @Positive(message = "La duración debe ser positiva")
            Integer durationMin,

            @Valid
            List<StopCreateRequest> stops
    ) implements Serializable {}

    public record RouteUpdateRequest(
            String name,
            Double distanceKm,
            Integer durationMin
    ) implements Serializable {}

    public record RouteResponse(
            Long id,
            String code,
            String name,
            String origin,
            String destination,
            Double distanceKm,
            Integer durationMin,
            List<StopResponse> stops
    ) implements Serializable {}

    public record StopCreateRequest(
            @NotBlank(message = "El nombre de la parada es requerido")
            String name,

            @NotNull(message = "El orden es requerido")
            @Min(value = 0, message = "El orden no puede ser negativo")
            Integer orderIndex,

            @NotNull(message = "La latitud es requerida")
            Double lat,

            @NotNull(message = "La longitud es requerida")
            Double lng
    ) implements Serializable {}

    public record StopResponse(
            Long id,
            String name,
            Integer orderIndex,
            Double lat,
            Double lng
    ) implements Serializable {}
}