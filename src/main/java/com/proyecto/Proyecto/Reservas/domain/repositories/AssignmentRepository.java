package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Optional<Assignment> findByTripId(Long tripId);
}
