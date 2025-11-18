package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Baggage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BaggageRepository extends JpaRepository<Baggage, Long> {
    List<Baggage> findByTicketId(Long ticketId);
}