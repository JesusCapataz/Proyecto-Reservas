package com.proyecto.Proyecto.Reservas.api;


import com.proyecto.Proyecto.Reservas.api.dto.TripDtos.TripResponse;
import com.proyecto.Proyecto.Reservas.domain.enums.TripStatus;
import com.proyecto.Proyecto.Reservas.domain.repositories.AssignmentRepository;
import com.proyecto.Proyecto.Reservas.domain.repositories.TripRepository;
import com.proyecto.Proyecto.Reservas.exception.NotFoundException;
import com.proyecto.Proyecto.Reservas.services.mapper.TripMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/trips/{tripId}")
@RequiredArgsConstructor
public class TripOperationsController {

    private final TripRepository tripRepository;
    private final AssignmentRepository assignmentRepository;
    private final TripMapper tripMapper;

    /**
     * POST /api/trips/{id}/boarding/open - abrir abordaje
     */
    @PostMapping("/boarding/open")
    public ResponseEntity<TripResponse> openBoarding(@PathVariable Long tripId) {

        var trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + tripId));

        // Validar que el trip esté en estado SCHEDULED
        if (trip.getStatus() != TripStatus.SCHEDULED) {
            throw new IllegalStateException("Trip must be in SCHEDULED status to open boarding. Current status: " + trip.getStatus());
        }

        // Validar que tenga una asignación
        var assignment = assignmentRepository.findByTripId(tripId);
        if (assignment.isEmpty()) {
            throw new IllegalStateException("Trip must have an assignment before opening boarding");
        }

        // Cambiar estado a BOARDING
        trip.setStatus(TripStatus.BOARDING);
        var updatedTrip = tripRepository.save(trip);

        return ResponseEntity.ok(tripMapper.toResponse(updatedTrip));
    }

    /**
     * POST /api/trips/{id}/boarding/close - cerrar abordaje
     */
    @PostMapping("/boarding/close")
    public ResponseEntity<TripResponse> closeBoarding(@PathVariable Long tripId) {

        var trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + tripId));

        // Validar que el trip esté en estado BOARDING
        if (trip.getStatus() != TripStatus.BOARDING) {
            throw new IllegalStateException("Trip must be in BOARDING status to close boarding. Current status: " + trip.getStatus());
        }

        // Cambiar estado de vuelta a SCHEDULED (o mantener BOARDING, según lógica de negocio)
        trip.setStatus(TripStatus.SCHEDULED);
        var updatedTrip = tripRepository.save(trip);

        return ResponseEntity.ok(tripMapper.toResponse(updatedTrip));
    }

    /**
     * POST /api/trips/{id}/depart - salida del viaje (valida checklist)
     */
    @PostMapping("/depart")
    public ResponseEntity<TripResponse> departTrip(@PathVariable Long tripId) {

        var trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + tripId));

        // Validar que el trip esté en estado BOARDING o SCHEDULED
        if (trip.getStatus() != TripStatus.BOARDING && trip.getStatus() != TripStatus.SCHEDULED) {
            throw new IllegalStateException("Trip must be in BOARDING or SCHEDULED status to depart. Current status: " + trip.getStatus());
        }

        // Validar que tenga una asignación con checklist OK
        var assignment = assignmentRepository.findByTripId(tripId)
                .orElseThrow(() -> new IllegalStateException("Trip must have an assignment before departing"));

        if (!assignment.getChecklistOk()) {
            throw new IllegalStateException("Trip cannot depart: checklist is not OK");
        }

        // Cambiar estado a DEPARTED
        trip.setStatus(TripStatus.DEPARTED);
        var updatedTrip = tripRepository.save(trip);

        return ResponseEntity.ok(tripMapper.toResponse(updatedTrip));
    }

    /**
     * POST /api/trips/{id}/arrive - llegada del viaje
     */
    @PostMapping("/arrive")
    public ResponseEntity<TripResponse> arriveTrip(@PathVariable Long tripId) {

        var trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + tripId));

        // Validar que el trip esté en estado DEPARTED
        if (trip.getStatus() != TripStatus.DEPARTED) {
            throw new IllegalStateException("Trip must be in DEPARTED status to arrive. Current status: " + trip.getStatus());
        }

        // Cambiar estado a ARRIVED
        trip.setStatus(TripStatus.ARRIVED);
        var updatedTrip = tripRepository.save(trip);

        return ResponseEntity.ok(tripMapper.toResponse(updatedTrip));
    }

    /**
     * POST /api/trips/{id}/cancel - cancelar viaje
     */
    @PostMapping("/cancel")
    public ResponseEntity<TripResponse> cancelTrip(@PathVariable Long tripId) {

        var trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + tripId));

        // No se puede cancelar un viaje que ya partió o llegó
        if (trip.getStatus() == TripStatus.DEPARTED || trip.getStatus() == TripStatus.ARRIVED) {
            throw new IllegalStateException("Cannot cancel a trip that has already departed or arrived");
        }

        // Cambiar estado a CANCELLED
        trip.setStatus(TripStatus.CANCELLED);
        var updatedTrip = tripRepository.save(trip);

        return ResponseEntity.ok(tripMapper.toResponse(updatedTrip));
    }
}



