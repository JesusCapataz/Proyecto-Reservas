package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    // Requerido para: GET /api/trips?routeId=&date=
    // Busca viajes de una ruta específica en una fecha específica
    List<Trip> findByRouteIdAndDate(Long routeId, LocalDate date);

    // Útil para mostrar salidas próximas
    List<Trip> findByDateBetween(LocalDate start, LocalDate end);
}