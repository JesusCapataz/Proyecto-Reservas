package com.proyecto.Proyecto.Reservas.api.dto;

import com.proyecto.Proyecto.Reservas.domain.enums.Role;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

public class UserDtos {

    public record UserCreateRequest(
            @NotBlank(message = "El nombre es requerido")
            @Size(max = 100, message = "El nombre no debe exceder 100 caracteres")
            String name,

            @NotBlank(message = "El email es requerido")
            @Email(message = "El email debe ser válido")
            String email,

            @NotBlank(message = "El teléfono es requerido")
            String phone,

            @NotNull(message = "El rol es requerido")
            Role role,

            @NotBlank(message = "La contraseña es requerida")
            @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
            String password
    ) implements Serializable {}

    public record UserUpdateRequest(
            String name,
            String email,
            String phone,
            Boolean status
    ) implements Serializable {}

    public record UserResponse(
            Long id,
            String name,
            String email,
            String phone,
            Role role,
            Boolean status,
            LocalDateTime createdAt
    ) implements Serializable {}

    public record UserLoginRequest(
            @NotBlank(message = "El email es requerido")
            @Email(message = "El email debe ser válido")
            String email,

            @NotBlank(message = "La contraseña es requerida")
            String password
    ) implements Serializable {}
}