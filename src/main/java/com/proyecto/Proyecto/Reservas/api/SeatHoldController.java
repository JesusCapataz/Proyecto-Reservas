package com.proyecto.Proyecto.Reservas.api;


import com.proyecto.Proyecto.Reservas.api.dto.SeatHoldDtos.*;
import com.proyecto.Proyecto.Reservas.services.SeatHoldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        var request = new SeatHoldCreateRequest(tripId, seatNumber, userId);
        var response = seatHoldService.holdSeat(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatHoldResponse> getHoldById(@PathVariable Long id) {
        var response = seatHoldService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trips/{tripId}")
    public ResponseEntity<List<SeatHoldResponse>> getHoldsByTrip(@PathVariable Long tripId) {
        var response = seatHoldService.getByTripId(tripId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trips/{tripId}/seats/{seatNumber}/availability")
    public ResponseEntity<SeatAvailabilityResponse> checkAvailability(
            @PathVariable Long tripId,
            @PathVariable Integer seatNumber) {
        var response = seatHoldService.checkSeatAvailability(tripId, seatNumber);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> releaseHold(@PathVariable Long id) {
        seatHoldService.releaseHold(id);
        return ResponseEntity.noContent().build();
    }
}



