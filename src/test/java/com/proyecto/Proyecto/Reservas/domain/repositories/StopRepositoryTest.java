package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Route;
import com.proyecto.Proyecto.Reservas.domain.entities.Stop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class StopRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired private StopRepository stopRepository;
    @Autowired private RouteRepository routeRepository;

    @BeforeEach
    void setUp() {
        stopRepository.deleteAll();
        routeRepository.deleteAll();
    }

    @Test
    void shouldFindStopsOrdered() {
        // Given
        Route route = Route.builder().code("R1").build();
        routeRepository.save(route);

        Stop stop2 = Stop.builder().name("Parada 2").orderIndex(2).route(route).build();
        Stop stop1 = Stop.builder().name("Parada 1").orderIndex(1).route(route).build();
        stopRepository.save(stop2);
        stopRepository.save(stop1);

        // When
        List<Stop> stops = stopRepository.findByRouteIdOrderByOrderIndexAsc(route.getId());

        // Then
        assertThat(stops).hasSize(2);
        assertThat(stops.get(0).getName()).isEqualTo("Parada 1"); // Debe ser el primero
        assertThat(stops.get(1).getName()).isEqualTo("Parada 2");
    }
}