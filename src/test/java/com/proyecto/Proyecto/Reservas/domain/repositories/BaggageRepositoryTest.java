package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Baggage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class BaggageRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired private BaggageRepository baggageRepository;

    @BeforeEach
    void setUp() { baggageRepository.deleteAll(); }

    @Test
    void shouldSaveAndFindBaggage() {
        // Given
        Baggage baggage = Baggage.builder()
                .weightKg(15.5)
                .fee(new BigDecimal("2000.00"))
                .tagCode("BAG-123")
                .build();
        baggageRepository.save(baggage);

        // When
        Optional<Baggage> found = baggageRepository.findById(baggage.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getTagCode()).isEqualTo("BAG-123");
    }
}