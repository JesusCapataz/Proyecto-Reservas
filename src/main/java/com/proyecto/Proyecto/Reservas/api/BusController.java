package com.proyecto.Proyecto.Reservas.api;


import com.proyecto.Proyecto.Reservas.api.dto.BusDtos.*;
import com.proyecto.Proyecto.Reservas.services.BusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/buses")
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;

    @PostMapping
    public ResponseEntity<BusResponse> createBus(@Valid @RequestBody BusCreateRequest request) {
        log.info("POST /api/buses - Creating bus with plate: {}", request.plate());
        var response = busService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BusResponse>> getAllBuses() {
        log.info("GET /api/buses - Getting all buses");
        var response = busService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusResponse> getBusById(@PathVariable Long id) {
        log.info("GET /api/buses/{} - Getting bus by id", id);
        var response = busService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/plate/{plate}")
    public ResponseEntity<BusResponse> getBusByPlate(@PathVariable String plate) {
        log.info("GET /api/buses/plate/{} - Getting bus by plate", plate);
        var response = busService.getByPlate(plate);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusResponse> updateBus(
            @PathVariable Long id,
            @Valid @RequestBody BusUpdateRequest request) {
        log.info("PUT /api/buses/{} - Updating bus", id);
        var response = busService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        log.info("DELETE /api/buses/{} - Deleting bus", id);
        busService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



