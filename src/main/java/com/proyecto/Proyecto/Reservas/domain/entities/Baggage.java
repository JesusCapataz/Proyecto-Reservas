package com.proyecto.Proyecto.Reservas.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "baggages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Baggage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double weightKg;
    private BigDecimal fee;
    private String tagCode;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
