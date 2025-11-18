package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.FareRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class FareRuleRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired private FareRuleRepository fareRuleRepository;

    @BeforeEach
    void setUp() { fareRuleRepository.deleteAll(); }

    @Test
    void shouldSaveAndFindFareRule() {
        // Given
        FareRule rule = FareRule.builder()
                .basePrice(50000.0)
                .dynamicPricing(true)
                .build();
        fareRuleRepository.save(rule);

        // When
        Optional<FareRule> found = fareRuleRepository.findById(rule.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getBasePrice()).isEqualTo(50000.0);
    }
}