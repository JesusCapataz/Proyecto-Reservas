package com.proyecto.Proyecto.Reservas.api.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

public class AssignmentDtos {

    public record AssignmentCreateRequest(
            @NotNull(message = "El viaje es requerido")
            Long tripId,

            @NotNull(message = "El conductor es requerido")
            Long driverId,

            @NotNull(message = "El despachador es requerido")
            Long dispatcherId,

            @NotNull(message = "El estado del checklist es requerido")
            Boolean checklistOk
    ) implements Serializable {}

    public record AssignmentUpdateRequest(
            Long driverId,
            Boolean checklistOk
    ) implements Serializable {}

    public record AssignmentResponse(
            Long id,
            Long tripId,
            UserInfo driver,
            UserInfo dispatcher,
            Boolean checklistOk,
            LocalDateTime assignedAt
    ) implements Serializable {}


    public record UserInfo(
            Long id,
            String name,
            String phone
    ) implements Serializable {}
}