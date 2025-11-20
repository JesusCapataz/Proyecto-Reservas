package com.proyecto.Proyecto.Reservas.api;


import com.proyecto.Proyecto.Reservas.api.dto.SeatHoldDtos.*;
import com.proyecto.Proyecto.Reservas.services.SeatHoldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/seat-holds")
@RequiredArgsConstructor
public class SeatHoldController {

    private final SeatHoldService seatHoldService;

    /**
     * POST /api/trips/{tripId}/seats/{seatNumber}/hold - bloquear asiento por 10 min
     */
    @PostMapping("/trips/{tripId}/seats/{seatNumber}/hold")
    public ResponseEntity<SeatHoldResponse> holdSeat(
            @PathVariable Long tripId,
            @PathVariable Integer seatNumber,
            @RequestParam Long userId) {
        log.info("POST /api/trips/{}/seats/{}/hold - Holding seat for user: {}",
                tripId, seatNumber, userId);

        var request = new SeatHoldCreateRequest(tripId, seatNumber, userId);
        var response = seatHoldService.holdSeat(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatHoldResponse> getHoldById(@PathVariable Long id) {
        log.info("GET /api/seat-holds/{} - Getting seat hold by id", id);
        var response = seatHoldService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trips/{tripId}")
    public ResponseEntity<List<SeatHoldResponse>> getHoldsByTrip(@PathVariable Long tripId) {
        log.info("GET /api/seat-holds/trips/{} - Getting holds for trip", tripId);
        var response = seatHoldService.getByTripId(tripId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trips/{tripId}/seats/{seatNumber}/availability")
    public ResponseEntity<SeatAvailabilityResponse> checkAvailability(
            @PathVariable Long tripId,
            @PathVariable Integer seatNumber) {
        log.info("GET /api/seat-holds/trips/{}/seats/{}/availability - Checking seat availability",
                tripId, seatNumber);
        var response = seatHoldService.checkSeatAvailability(tripId, seatNumber);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> releaseHold(@PathVariable Long id) {
        log.info("DELETE /api/seat-holds/{} - Releasing seat hold", id);
        seatHoldService.releaseHold(id);
        return ResponseEntity.noContent().build();
    }
}



