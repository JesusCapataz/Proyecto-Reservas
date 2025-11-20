package com.proyecto.Proyecto.Reservas.api;


import com.proyecto.Proyecto.Reservas.api.dto.AssignmentDtos.*;
import com.proyecto.Proyecto.Reservas.services.AssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    /**
     * POST /api/trips/{tripId}/assign (DISPATCHER) - asignar bus/driver a viaje
     */
    @PostMapping
    public ResponseEntity<AssignmentResponse> createAssignment(
            @Valid @RequestBody AssignmentCreateRequest request) {
        log.info("POST /api/assignments - Creating assignment for trip: {}", request.tripId());
        var response = assignmentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> getAllAssignments() {
        log.info("GET /api/assignments - Getting all assignments");
        var response = assignmentService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> getAssignmentById(@PathVariable Long id) {
        log.info("GET /api/assignments/{} - Getting assignment by id", id);
        var response = assignmentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trips/{tripId}")
    public ResponseEntity<AssignmentResponse> getAssignmentByTrip(@PathVariable Long tripId) {
        log.info("GET /api/assignments/trips/{} - Getting assignment for trip", tripId);
        var response = assignmentService.getByTripId(tripId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentResponse> updateAssignment(
            @PathVariable Long id,
            @Valid @RequestBody AssignmentUpdateRequest request) {
        log.info("PUT /api/assignments/{} - Updating assignment", id);
        var response = assignmentService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        log.info("DELETE /api/assignments/{} - Deleting assignment", id);
        assignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



