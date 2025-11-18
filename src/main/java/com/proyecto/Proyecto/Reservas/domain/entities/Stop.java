package com.proyecto.Proyecto.Reservas.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stops")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer orderIndex;
    private Double lat;
    private Double lng;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
}