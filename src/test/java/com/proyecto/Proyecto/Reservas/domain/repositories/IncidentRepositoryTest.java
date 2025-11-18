package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Incident;
import com.proyecto.Proyecto.Reservas.domain.enums.IncidentType;
import com.proyecto.Proyecto.Reservas.domain.enums.IncidentEntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class IncidentRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired private IncidentRepository incidentRepository;

    @BeforeEach
    void setUp() { incidentRepository.deleteAll(); }

    @Test
    void shouldSaveAndFindIncident() {
        // Given
        Incident incident = Incident.builder()
                .type(IncidentType.VEHICLE)
                .entityType(IncidentEntityType.TRIP) // O lo que tengas en tu Enum
                .entityId(100L)
                .note("Falla de motor")
                .createdAt(LocalDateTime.now())
                .build();
        incidentRepository.save(incident);

        // When
        Optional<Incident> found = incidentRepository.findById(incident.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getNote()).isEqualTo("Falla de motor");
    }
}