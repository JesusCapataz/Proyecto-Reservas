package com.proyecto.Proyecto.Reservas.domain.entities;

import com.proyecto.Proyecto.Reservas.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean status;

    @Column(nullable =false)
    private String passwordHash;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = true;
        }
    }
}