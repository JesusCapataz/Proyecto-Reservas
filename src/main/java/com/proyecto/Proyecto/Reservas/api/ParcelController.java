package com.proyecto.Proyecto.Reservas.api;


import com.proyecto.Proyecto.Reservas.api.dto.ParcelDtos.*;
import com.proyecto.Proyecto.Reservas.services.ParcelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        var response = parcelService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ParcelResponse>> getAllParcels() {

        var response = parcelService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParcelResponse> getParcelById(@PathVariable Long id) {

        var response = parcelService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ParcelResponse> getParcelByCode(@PathVariable String code) {
        var response = parcelService.getByCode(code);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/parcels/{code}/track - rastrear encomienda
     */
    @GetMapping("/{code}/track")
    public ResponseEntity<ParcelTrackingResponse> trackParcel(@PathVariable String code) {
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
        var response = parcelService.deliverParcel(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParcelResponse> updateParcel(
            @PathVariable Long id,
            @Valid @RequestBody ParcelUpdateRequest request) {
        var response = parcelService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcel(@PathVariable Long id) {
        parcelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



