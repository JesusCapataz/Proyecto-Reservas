package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Ticket;
import com.proyecto.Proyecto.Reservas.domain.enums.TicketStatus;
import com.proyecto.Proyecto.Reservas.domain.enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TicketRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired
    private TicketRepository ticketRepository;
    // Necesitaríamos TripRepository, UserRepository, etc., si las relaciones fueran obligatorias en la BD
    // (nullable=false). Si lo son, debes crearlos como en el ejemplo anterior.

    @BeforeEach
    void setUp() {
        ticketRepository.deleteAll();
    }

    @Test
    void shouldFindTicketByQrCode() {
        // Given
        Ticket ticket = Ticket.builder()
                .seatNumber(5)
                .price(new BigDecimal("50.00"))
                .qrCode("QR-XYZ-123")
                .status(TicketStatus.SOLD)
                .paymentMethod(PaymentMethod.QR)
                .build();
        // Nota: Si Ticket tiene relaciones @ManyToOne obligatorias, debes crear y guardar los padres primero.
        ticketRepository.save(ticket);

        // When
        Optional<Ticket> found = ticketRepository.findByQrCode("QR-XYZ-123");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getSeatNumber()).isEqualTo(5);
    }

    @Test
    void shouldCountSoldTickets() {
        // Given: 1 Tiquete vendido y 1 Cancelado para el mismo Trip ID (simulado como 1L)
        // *IMPORTANTE*: En una prueba real con integridad referencial, debes crear un Trip real primero.

        Ticket t1 = Ticket.builder().status(TicketStatus.SOLD).build(); // Asume trip_id nullable o usa mock trip
        Ticket t2 = Ticket.builder().status(TicketStatus.CANCELLED).build();

        ticketRepository.save(t1);
        ticketRepository.save(t2);

        // When
        long soldCount = ticketRepository.countByTripIdAndStatus(null, TicketStatus.SOLD);
        // Usamos null como tripId porque no asignamos uno,
        // pero en tu código real usarías el trip.getId()

        // Then
        assertThat(soldCount).isEqualTo(1);
    }
}