package com.proyecto.Proyecto.Reservas.api;


import com.proyecto.Proyecto.Reservas.api.dto.TripDtos.*;
import com.proyecto.Proyecto.Reservas.services.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<TripResponse> createTrip(@Valid @RequestBody TripCreateRequest request) {
        log.info("POST /api/trips - Creating trip for route: {} on date: {}",
                request.routeId(), request.date());
        var response = tripService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> searchTrips(
            @RequestParam(required = false) Long routeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("GET /api/trips - Searching trips with routeId: {} and date: {}", routeId, date);
        var response = tripService.searchTrips(routeId, date);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponse> getTripById(@PathVariable Long id) {
        log.info("GET /api/trips/{} - Getting trip by id", id);
        var response = tripService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripResponse> updateTrip(
            @PathVariable Long id,
            @Valid @RequestBody TripUpdateRequest request) {
        log.info("PUT /api/trips/{} - Updating trip", id);
        var response = tripService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        log.info("DELETE /api/trips/{} - Deleting trip", id);
        tripService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



