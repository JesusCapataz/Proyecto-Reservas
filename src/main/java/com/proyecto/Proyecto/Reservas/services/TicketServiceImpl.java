package com.proyecto.Proyecto.Reservas.services;


import com.proyecto.Proyecto.Reservas.api.dto.TicketDtos.*;
import com.proyecto.Proyecto.Reservas.domain.enums.SeatHoldStatus;
import com.proyecto.Proyecto.Reservas.domain.enums.TicketStatus;
import com.proyecto.Proyecto.Reservas.domain.repositories.*;
import com.proyecto.Proyecto.Reservas.exception.NotFoundException;
import com.proyecto.Proyecto.Reservas.services.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final StopRepository stopRepository;
    private final SeatHoldRepository seatHoldRepository;
    private final TicketMapper ticketMapper;

    @Override
    public TicketResponse createTicket(TicketCreateRequest request) {

        // Validar que el trip existe
        var trip = tripRepository.findById(request.tripId())
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + request.tripId()));

        // Validar que el passenger existe
        var passenger = userRepository.findById(request.passengerId())
                .orElseThrow(() -> new NotFoundException("Passenger not found with id: " + request.passengerId()));

        // Validar las paradas
        var fromStop = stopRepository.findById(request.fromStopId())
                .orElseThrow(() -> new NotFoundException("From stop not found with id: " + request.fromStopId()));

        var toStop = stopRepository.findById(request.toStopId())
                .orElseThrow(() -> new NotFoundException("To stop not found with id: " + request.toStopId()));

        // Validar que las paradas pertenecen a la ruta del trip
        if (!fromStop.getRoute().getId().equals(trip.getRoute().getId()) ||
                !toStop.getRoute().getId().equals(trip.getRoute().getId())) {
            throw new IllegalArgumentException("Stops must belong to the trip's route");
        }

        // Validar que el orden de las paradas sea correcto
        if (fromStop.getOrderIndex() >= toStop.getOrderIndex()) {
            throw new IllegalArgumentException("From stop must be before to stop");
        }

        // Validar que el número de asiento sea válido
        if (request.seatNumber() <= 0 || request.seatNumber() > trip.getBus().getCapacity()) {
            throw new IllegalArgumentException("Invalid seat number: " + request.seatNumber());
        }

        // Verificar que el asiento no esté ya vendido
        boolean isSeatSold = ticketRepository.existsByTripIdAndSeatNumberAndStatus(
                request.tripId(), request.seatNumber(), TicketStatus.SOLD);

        if (isSeatSold) {
            throw new IllegalStateException("Seat " + request.seatNumber() + " is already sold");
        }

        // Si viene de un hold, validarlo y liberarlo
        if (request.holdId() != null) {
            var hold = seatHoldRepository.findById(request.holdId())
                    .orElseThrow(() -> new NotFoundException("Hold not found with id: " + request.holdId()));

            if (!hold.getTrip().getId().equals(request.tripId()) ||
                    !hold.getSeatNumber().equals(request.seatNumber())) {
                throw new IllegalArgumentException("Hold does not match trip and seat");
            }

            if (hold.getExpiresAt().isBefore(LocalDateTime.now())) {
                throw new IllegalStateException("Hold has expired");
            }

            // Liberar el hold
            hold.setStatus(SeatHoldStatus.EXPIRED);
            seatHoldRepository.save(hold);
        }

        var ticket = ticketMapper.toEntity(request);
        ticket.setTrip(trip);
        ticket.setPassenger(passenger);
        ticket.setFromStop(fromStop);
        ticket.setToStop(toStop);
        ticket.setQrCode(generateQrCode());

        var savedTicket = ticketRepository.save(ticket);

        return ticketMapper.toResponse(savedTicket);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponse getById(Long id) {
        return ticketRepository.findById(id)
                .map(ticketMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Ticket not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponse getByQrCode(String qrCode) {
        return ticketRepository.findByQrCode(qrCode)
                .map(ticketMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Ticket not found with QR code: " + qrCode));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> getByTripId(Long tripId) {

        if (!tripRepository.existsById(tripId)) {
            throw new NotFoundException("Trip not found with id: " + tripId);
        }

        var tickets = ticketRepository.findByTripId(tripId);
        return ticketMapper.toResponseList(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> getByPassengerId(Long passengerId) {

        if (!userRepository.existsById(passengerId)) {
            throw new NotFoundException("Passenger not found with id: " + passengerId);
        }

        var tickets = ticketRepository.findByPassengerId(passengerId);
        return ticketMapper.toResponseList(tickets);
    }

    @Override
    public TicketResponse updateStatus(Long id, TicketUpdateRequest request) {

        var ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found with id: " + id));

        ticketMapper.updateEntityFromDto(request, ticket);
        var updatedTicket = ticketRepository.save(ticket);

        return ticketMapper.toResponse(updatedTicket);
    }

    @Override
    public TicketResponse cancelTicket(Long id) {

        var ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found with id: " + id));

        if (ticket.getStatus() == TicketStatus.CANCELLED) {
            throw new IllegalStateException("Ticket is already cancelled");
        }

        ticket.setStatus(TicketStatus.CANCELLED);
        var cancelledTicket = ticketRepository.save(ticket);

        return ticketMapper.toResponse(cancelledTicket);
    }

    @Override
    public void delete(Long id) {

        if (!ticketRepository.existsById(id)) {
            throw new NotFoundException("Ticket not found with id: " + id);
        }

        ticketRepository.deleteById(id);
    }

    private String generateQrCode() {
        return "QR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}




