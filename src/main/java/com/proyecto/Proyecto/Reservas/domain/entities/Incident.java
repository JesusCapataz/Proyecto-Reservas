package com.proyecto.Proyecto.Reservas.domain.entities;

import com.proyecto.Proyecto.Reservas.domain.enums.IncidentType;
import com.proyecto.Proyecto.Reservas.domain.enums.IncidentEntityType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private IncidentEntityType entityType;

    private Long entityId;

    @Enumerated(EnumType.STRING)
    private IncidentType type;

    private String note;
    private LocalDateTime createdAt;
}