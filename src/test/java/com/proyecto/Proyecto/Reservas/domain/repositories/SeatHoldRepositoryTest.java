package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.SeatHold;
import com.proyecto.Proyecto.Reservas.domain.enums.SeatHoldStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class SeatHoldRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired private SeatHoldRepository seatHoldRepository;

    @BeforeEach
    void setUp() {
        seatHoldRepository.deleteAll();
    }

    @Test
    void shouldFindActiveHoldForSeat() {
        // Given
        SeatHold hold = SeatHold.builder()
                .seatNumber(10)
                .status(SeatHoldStatus.HOLD)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .build();
        // Nota: En un test real, deberías crear y asignar el Trip y el User aquí
        seatHoldRepository.save(hold);

        // When
        // Usamos null para tripId solo por el ejemplo, en real usarías trip.getId()
        Optional<SeatHold> found = seatHoldRepository.findByTripIdAndSeatNumberAndStatus(null, 10, SeatHoldStatus.HOLD);

        // Then
        assertThat(found).isPresent();
    }

    @Test
    void shouldFindExpiredHolds() {
        // Given: Un hold que venció ayer
        SeatHold expiredHold = SeatHold.builder()
                .status(SeatHoldStatus.HOLD)
                .expiresAt(LocalDateTime.now().minusDays(1))
                .build();
        seatHoldRepository.save(expiredHold);

        // When
        List<SeatHold> expired = seatHoldRepository.findByStatusAndExpiresAtBefore(SeatHoldStatus.HOLD, LocalDateTime.now());

        // Then
        assertThat(expired).hasSize(1);
    }
}