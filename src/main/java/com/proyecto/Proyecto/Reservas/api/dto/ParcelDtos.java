package com.proyecto.Proyecto.Reservas.api.dto;

import com.proyecto.Proyecto.Reservas.domain.enums.ParcelStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParcelDtos {

    public record ParcelCreateRequest(
            @NotBlank(message = "El nombre del remitente es requerido")
            String senderName,

            @NotBlank(message = "El teléfono del remitente es requerido")
            String senderPhone,

            @NotBlank(message = "El nombre del destinatario es requerido")
            String receiverName,

            @NotBlank(message = "El teléfono del destinatario es requerido")
            String receiverPhone,

            @NotNull(message = "La parada de origen es requerida")
            Long fromStopId,

            @NotNull(message = "La parada de destino es requerida")
            Long toStopId,

            @NotNull(message = "El viaje es requerido")
            Long tripId,

            @NotNull(message = "El precio es requerido")
            @Positive(message = "El precio debe ser positivo")
            BigDecimal price
    ) implements Serializable {}

    public record ParcelUpdateRequest(
            ParcelStatus status,
            String proofPhotoUrl
    ) implements Serializable {}

    public record ParcelResponse(
            Long id,
            String code,
            String senderName,
            String senderPhone,
            String receiverName,
            String receiverPhone,
            StopInfo fromStop,
            StopInfo toStop,
            Long tripId,
            BigDecimal price,
            ParcelStatus status,
            String proofPhotoUrl,
            String deliveryOtp,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) implements Serializable {}

    public record ParcelDeliveryRequest(
            @NotBlank(message = "El OTP es requerido")
            @Size(min = 6, max = 6, message = "El OTP debe tener 6 caracteres")
            String otp,

            @NotBlank(message = "La foto de prueba es requerida")
            String proofPhotoUrl
    ) implements Serializable {}

    public record ParcelTrackingResponse(
            String code,
            ParcelStatus status,
            String senderName,
            String receiverName,
            String fromStopName,
            String toStopName
    ) implements Serializable {}


    public record StopInfo(
            Long id,
            String name
    ) implements Serializable {}
}