package com.proyecto.Proyecto.Reservas.api.dto;

import com.proyecto.Proyecto.Reservas.domain.enums.IncidentType;
import com.proyecto.Proyecto.Reservas.domain.enums.IncidentEntityType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

public class IncidentDtos {

    public record IncidentCreateRequest(
            @NotNull(message = "El tipo de entidad es requerido")
            IncidentEntityType entityType,

            @NotNull(message = "El ID de la entidad es requerido")
            Long entityId,

            @NotNull(message = "El tipo de incidente es requerido")
            IncidentType type,

            @NotBlank(message = "La nota es requerida")
            @Size(max = 1000, message = "La nota no debe exceder 1000 caracteres")
            String note
    ) implements Serializable {}

    public record IncidentUpdateRequest(
            String note
    ) implements Serializable {}

    public record IncidentResponse(
            Long id,
            IncidentEntityType entityType,
            Long entityId,
            IncidentType type,
            String note,
            LocalDateTime createdAt
    ) implements Serializable {}
}