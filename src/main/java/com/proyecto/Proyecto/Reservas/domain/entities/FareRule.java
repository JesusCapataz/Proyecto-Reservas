package com.proyecto.Proyecto.Reservas.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fare_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FareRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "from_stop_id")
    private Stop fromStop;

    @ManyToOne
    @JoinColumn(name = "to_stop_id")
    private Stop toStop;

    private Double basePrice;
    private String discounts;
    private Boolean dynamicPricing;
}