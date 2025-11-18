package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class RouteRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired private RouteRepository routeRepository;

    @BeforeEach
    void setUp() { routeRepository.deleteAll(); }

    @Test
    void shouldFindRouteByCode() {
        // Given
        Route route = Route.builder().code("MED-CAL").name("Medellin-Cali").build();
        routeRepository.save(route);

        // When
        Optional<Route> found = routeRepository.findByCode("MED-CAL");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Medellin-Cali");
    }
}