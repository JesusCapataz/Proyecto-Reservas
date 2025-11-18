package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class ConfigRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired private ConfigRepository configRepository;

    @BeforeEach
    void setUp() { configRepository.deleteAll(); }

    @Test
    void shouldSaveAndFindConfigByKey() {
        // Given
        Config config = Config.builder()
                .keyConfig("MAX_OVERBOOKING") // Este es el ID
                .valueConfig("0.05")
                .build();
        configRepository.save(config);

        // When
        // Probamos el método findById (que usa la llave) y el personalizado si lo tienes
        Optional<Config> found = configRepository.findByKeyConfig("MAX_OVERBOOKING");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getValueConfig()).isEqualTo("0.05");
    }
}