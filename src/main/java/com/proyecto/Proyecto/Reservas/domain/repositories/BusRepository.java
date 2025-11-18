package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus, Long> {
    Optional<Bus> findByPlate(String plate);
}