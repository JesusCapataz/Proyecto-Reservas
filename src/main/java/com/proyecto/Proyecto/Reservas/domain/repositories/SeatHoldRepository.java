package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.SeatHold;
import com.proyecto.Proyecto.Reservas.domain.enums.SeatHoldStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeatHoldRepository extends JpaRepository<SeatHold, Long> {
    Optional<SeatHold> findByTripIdAndSeatNumberAndStatus(Long tripId, Integer seatNumber, SeatHoldStatus status);
    List<SeatHold> findByStatusAndExpiresAtBefore(SeatHoldStatus status, LocalDateTime now);
    List<SeatHold> findByTripId(Long tripId);
}