package com.proyecto.Proyecto.Reservas.domain.entities;

import com.proyecto.Proyecto.Reservas.domain.enums.TripStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    private LocalDate date;
    private LocalDateTime departureAt;
    private LocalDateTime arrivalEta;

    @Enumerated(EnumType.STRING)
    private TripStatus status;
}