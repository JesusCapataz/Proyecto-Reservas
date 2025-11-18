package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Bus;
import com.proyecto.Proyecto.Reservas.domain.entities.Route;
import com.proyecto.Proyecto.Reservas.domain.entities.Trip;
import com.proyecto.Proyecto.Reservas.domain.enums.BusStatus;
import com.proyecto.Proyecto.Reservas.domain.enums.TripStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TripRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired private TripRepository tripRepository;
    @Autowired private RouteRepository routeRepository;
    @Autowired private BusRepository busRepository;

    @BeforeEach
    void setUp() {
        tripRepository.deleteAll();
        routeRepository.deleteAll();
        busRepository.deleteAll();
    }

    @Test
    void shouldFindTripsByRouteAndDate() {
        // 1. Crear y Guardar Padres (Route y Bus)
        Route route = Route.builder().code("BOG-TUN").name("Bogota-Tunja").origin("Bogota").destination("Tunja").build();
        routeRepository.save(route);

        Bus bus = Bus.builder().plate("BUS-123").capacity(40).status(BusStatus.ACTIVE).build();
        busRepository.save(bus);

        // 2. Crear y Guardar Trips
        LocalDate today = LocalDate.now();

        Trip trip1 = Trip.builder()
                .route(route).bus(bus).date(today).status(TripStatus.SCHEDULED)
                .departureAt(LocalDateTime.now().plusHours(2))
                .build();

        Trip trip2 = Trip.builder()
                .route(route).bus(bus).date(today.plusDays(1)) // Mañana
                .status(TripStatus.SCHEDULED)
                .build();

        tripRepository.save(trip1);
        tripRepository.save(trip2);

        // 3. Probar la consulta personalizada
        List<Trip> result = tripRepository.findByRouteIdAndDate(route.getId(), today);

        // 4. Verificar
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDate()).isEqualTo(today);
    }
}