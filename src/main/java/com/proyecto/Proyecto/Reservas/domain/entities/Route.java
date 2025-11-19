package com.proyecto.Proyecto.Reservas.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "routes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String name;
    private String origin;
    private String destination;
    private Double distanceKm;
    private Integer durationMin;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<Stop> stops;

    public void addStop(Stop stop) {
        stops.add(stop);
        stop.setRoute(this);
    }

    public void removeStop(Stop stop) {
        stops.remove(stop);
        stop.setRoute(null);
    }
}