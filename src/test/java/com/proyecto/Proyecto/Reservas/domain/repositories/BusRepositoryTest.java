package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Bus;
import com.proyecto.Proyecto.Reservas.domain.enums.BusStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class BusRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired private BusRepository busRepository;

    @BeforeEach
    void setUp() { busRepository.deleteAll(); }

    @Test
    void shouldFindBusByPlate() {
        // Given
        Bus bus = Bus.builder().plate("XYZ-123").capacity(40).status(BusStatus.ACTIVE).build();
        busRepository.save(bus);

        // When
        Optional<Bus> found = busRepository.findByPlate("XYZ-123");

        // Then
        assertThat(found).isPresent();
    }
}