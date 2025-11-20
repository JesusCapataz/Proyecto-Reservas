package com.proyecto.Proyecto.Reservas.services;


import com.proyecto.Proyecto.Reservas.api.dto.SeatHoldDtos.*;
import com.proyecto.Proyecto.Reservas.domain.entities.SeatHold;
import com.proyecto.Proyecto.Reservas.domain.enums.SeatHoldStatus;
import com.proyecto.Proyecto.Reservas.domain.enums.TicketStatus;
import com.proyecto.Proyecto.Reservas.domain.repositories.SeatHoldRepository;
import com.proyecto.Proyecto.Reservas.domain.repositories.TicketRepository;
import com.proyecto.Proyecto.Reservas.domain.repositories.TripRepository;
import com.proyecto.Proyecto.Reservas.domain.repositories.UserRepository;
import com.proyecto.Proyecto.Reservas.exception.NotFoundException;
import com.proyecto.Proyecto.Reservas.services.mapper.SeatHoldMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SeatHoldServiceImpl implements SeatHoldService {

    private final SeatHoldRepository seatHoldRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final SeatHoldMapper seatHoldMapper;

    private static final int HOLD_DURATION_MINUTES = 10;

    @Override
    public SeatHoldResponse holdSeat(SeatHoldCreateRequest request) {
        log.info("Holding seat {} for trip {} and user {}",
                request.seatNumber(), request.tripId(), request.userId());

        // Validar que el trip existe
        var trip = tripRepository.findById(request.tripId())
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + request.tripId()));

        // Validar que el usuario existe
        var user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + request.userId()));

        // Validar que el número de asiento sea válido
        if (request.seatNumber() <= 0 || request.seatNumber() > trip.getBus().getCapacity()) {
            throw new IllegalArgumentException("Invalid seat number: " + request.seatNumber());
        }

        // Verificar que el asiento no esté vendido
        boolean isSeatSold = ticketRepository.existsByTripIdAndSeatNumberAndStatus(
                request.tripId(), request.seatNumber(), TicketStatus.SOLD);

        if (isSeatSold) {
            throw new IllegalStateException("Seat " + request.seatNumber() + " is already sold");
        }

        // Verificar que no haya un hold activo para este asiento
        var existingHold = seatHoldRepository.findByTripIdAndSeatNumberAndStatus(
                request.tripId(), request.seatNumber(), SeatHoldStatus.HOLD);

        if (existingHold.isPresent()) {
            var hold = existingHold.get();
            if (hold.getExpiresAt().isAfter(LocalDateTime.now())) {
                throw new IllegalStateException("Seat " + request.seatNumber() + " is currently on hold");
            }
        }

        var seatHold = seatHoldMapper.toEntity(request);
        seatHold.setTrip(trip);
        seatHold.setUser(user);
        seatHold.setExpiresAt(LocalDateTime.now().plusMinutes(HOLD_DURATION_MINUTES));

        var savedHold = seatHoldRepository.save(seatHold);
        log.info("Seat hold created with id: {} expires at: {}",
                savedHold.getId(), savedHold.getExpiresAt());

        return seatHoldMapper.toResponse(savedHold);
    }

    @Override
    @Transactional(readOnly = true)
    public SeatHoldResponse getById(Long id) {
        log.info("Getting seat hold by id: {}", id);
        return seatHoldRepository.findById(id)
                .map(seatHoldMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Seat hold not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeatHoldResponse> getByTripId(Long tripId) {
        log.info("Getting seat holds for trip id: {}", tripId);

        if (!tripRepository.existsById(tripId)) {
            throw new NotFoundException("Trip not found with id: " + tripId);
        }

        var holds = seatHoldRepository.findByTripId(tripId);
        return seatHoldMapper.toResponseList(holds);
    }

    @Override
    @Transactional(readOnly = true)
    public SeatAvailabilityResponse checkSeatAvailability(Long tripId, Integer seatNumber) {
        log.info("Checking availability for seat {} in trip {}", seatNumber, tripId);

        // Verificar si está vendido
        boolean isSold = ticketRepository.existsByTripIdAndSeatNumberAndStatus(
                tripId, seatNumber, TicketStatus.SOLD);

        if (isSold) {
            return new SeatAvailabilityResponse(seatNumber, SeatHoldStatus.EXPIRED); // Usamos EXPIRED para indicar no disponible
        }

        // Verificar si hay un hold activo
        var hold = seatHoldRepository.findByTripIdAndSeatNumberAndStatus(
                tripId, seatNumber, SeatHoldStatus.HOLD);

        if (hold.isPresent() && hold.get().getExpiresAt().isAfter(LocalDateTime.now())) {
            return new SeatAvailabilityResponse(seatNumber, SeatHoldStatus.HOLD);
        }

        return new SeatAvailabilityResponse(seatNumber, SeatHoldStatus.EXPIRED); // Disponible
    }

    @Override
    public void expireHolds() {
        log.info("Expiring seat holds");

        var expiredHolds = seatHoldRepository.findByStatusAndExpiresAtBefore(
                SeatHoldStatus.HOLD, LocalDateTime.now());

        for (SeatHold hold : expiredHolds) {
            hold.setStatus(SeatHoldStatus.EXPIRED);
            seatHoldRepository.save(hold);
        }

        log.info("Expired {} seat holds", expiredHolds.size());
    }

    @Override
    public void releaseHold(Long holdId) {
        log.info("Releasing seat hold with id: {}", holdId);

        var hold = seatHoldRepository.findById(holdId)
                .orElseThrow(() -> new NotFoundException("Seat hold not found with id: " + holdId));

        hold.setStatus(SeatHoldStatus.EXPIRED);
        seatHoldRepository.save(hold);

        log.info("Seat hold released with id: {}", holdId);
    }
}



