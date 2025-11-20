package com.proyecto.Proyecto.Reservas.api;

import com.proyecto.Proyecto.Reservas.api.dto.AssignmentDtos.*;
import com.proyecto.Proyecto.Reservas.services.AssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('DISPATCHER')")
public class AssignmentController {

    private final AssignmentService assignmentService;

    /**
     * POST /api/trips/{tripId}/assign (DISPATCHER) - asignar bus/driver a viaje
     */
    @PostMapping
    public ResponseEntity<AssignmentResponse> createAssignment(
            @Valid @RequestBody AssignmentCreateRequest request) {
        var response = assignmentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> getAllAssignments() {
        var response = assignmentService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> getAssignmentById(@PathVariable Long id) {
        var response = assignmentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trips/{tripId}")
    public ResponseEntity<AssignmentResponse> getAssignmentByTrip(@PathVariable Long tripId) {
        var response = assignmentService.getByTripId(tripId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentResponse> updateAssignment(
            @PathVariable Long id,
            @Valid @RequestBody AssignmentUpdateRequest request) {
        var response = assignmentService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}