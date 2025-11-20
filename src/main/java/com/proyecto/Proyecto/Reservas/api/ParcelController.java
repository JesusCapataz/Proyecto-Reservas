package com.proyecto.Proyecto.Reservas.api;


import com.proyecto.Proyecto.Reservas.api.dto.ParcelDtos.*;
import com.proyecto.Proyecto.Reservas.services.ParcelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/parcels")
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    /**
     * POST /api/parcels - crear envío de encomienda
     */
    @PostMapping
    public ResponseEntity<ParcelResponse> createParcel(@Valid @RequestBody ParcelCreateRequest request) {
        log.info("POST /api/parcels - Creating parcel for trip: {}", request.tripId());
        var response = parcelService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ParcelResponse>> getAllParcels() {
        log.info("GET /api/parcels - Getting all parcels");
        var response = parcelService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParcelResponse> getParcelById(@PathVariable Long id) {
        log.info("GET /api/parcels/{} - Getting parcel by id", id);
        var response = parcelService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ParcelResponse> getParcelByCode(@PathVariable String code) {
        log.info("GET /api/parcels/code/{} - Getting parcel by code", code);
        var response = parcelService.getByCode(code);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/parcels/{code}/track - rastrear encomienda
     */
    @GetMapping("/{code}/track")
    public ResponseEntity<ParcelTrackingResponse> trackParcel(@PathVariable String code) {
        log.info("GET /api/parcels/{}/track - Tracking parcel", code);
        var response = parcelService.trackParcel(code);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/parcels/{id}/deliver - entregar encomienda con OTP y foto
     * Similar a: POST /api/parcels/{code}/status del documento
     */
    @PostMapping("/{id}/deliver")
    public ResponseEntity<ParcelResponse> deliverParcel(
            @PathVariable Long id,
            @Valid @RequestBody ParcelDeliveryRequest request) {
        log.info("POST /api/parcels/{}/deliver - Delivering parcel with OTP", id);
        var response = parcelService.deliverParcel(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParcelResponse> updateParcel(
            @PathVariable Long id,
            @Valid @RequestBody ParcelUpdateRequest request) {
        log.info("PUT /api/parcels/{} - Updating parcel status", id);
        var response = parcelService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcel(@PathVariable Long id) {
        log.info("DELETE /api/parcels/{} - Deleting parcel", id);
        parcelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



