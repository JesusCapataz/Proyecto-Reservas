package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Seat;
import com.proyecto.Proyecto.Reservas.domain.enums.SeatType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class SeatRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired private SeatRepository seatRepository;

    @BeforeEach
    void setUp() { seatRepository.deleteAll(); }

    @Test
    void shouldSaveAndFindSeat() {
        // Given
        Seat seat = Seat.builder()
                .number(25)
                .type(SeatType.STANDARD)
                .build();
        seatRepository.save(seat);

        // When
        // Como no asignamos Bus, probamos el findAll o findById
        List<Seat> seats = seatRepository.findAll();

        // Then
        assertThat(seats).hasSize(1);
        assertThat(seats.get(0).getNumber()).isEqualTo(25);
    }
}