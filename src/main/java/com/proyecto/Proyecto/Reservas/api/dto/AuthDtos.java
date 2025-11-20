package com.proyecto.Proyecto.Reservas.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

public class AuthDtos {

    public record LoginRequest(
            @NotBlank(message = "El email es requerido")
            @Email(message = "El email debe ser válido")
            String email,

            @NotBlank(message = "La contraseña es requerida")
            String password
    ) implements Serializable {}

    public record LoginResponse(
            String token,
            String type,
            Long expiresIn,
            UserInfo user
    ) implements Serializable {}

    public record UserInfo(
            Long id,
            String name,
            String email,
            String role
    ) implements Serializable {}
}