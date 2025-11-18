package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Ticket;
import com.proyecto.Proyecto.Reservas.domain.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsByTripIdAndSeatNumberAndStatus(Long tripId, Integer seatNumber, TicketStatus status);
    long countByTripIdAndStatus(Long tripId, TicketStatus status);
    List<Ticket> findByTripId(Long tripId);
    List<Ticket> findByPassengerId(Long passengerId);
    Optional<Ticket> findByQrCode(String qrCode);
    List<Ticket> findByTripIdAndSeatNumber(Long tripId, Integer seatNumber);
}