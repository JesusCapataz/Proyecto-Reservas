package com.proyecto.Proyecto.Reservas.api;


import com.proyecto.Proyecto.Reservas.api.dto.BusDtos.*;
import com.proyecto.Proyecto.Reservas.services.BusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;

    @PostMapping
    public ResponseEntity<BusResponse> createBus(@Valid @RequestBody BusCreateRequest request) {
        var response = busService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BusResponse>> getAllBuses() {
        var response = busService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusResponse> getBusById(@PathVariable Long id) {
        var response = busService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/plate/{plate}")
    public ResponseEntity<BusResponse> getBusByPlate(@PathVariable String plate) {
        var response = busService.getByPlate(plate);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusResponse> updateBus(
            @PathVariable Long id,
            @Valid @RequestBody BusUpdateRequest request) {
        var response = busService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        busService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



