package com.proyecto.Proyecto.Reservas.services;

import com.proyecto.Proyecto.Reservas.api.dto.AssignmentDtos.*;
import com.proyecto.Proyecto.Reservas.domain.enums.Role;
import com.proyecto.Proyecto.Reservas.domain.repositories.AssignmentRepository;
import com.proyecto.Proyecto.Reservas.domain.repositories.TripRepository;
import com.proyecto.Proyecto.Reservas.domain.repositories.UserRepository;
import com.proyecto.Proyecto.Reservas.exception.NotFoundException;
import com.proyecto.Proyecto.Reservas.services.mapper.AssignmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final AssignmentMapper assignmentMapper;

    @Override
    public AssignmentResponse create(AssignmentCreateRequest request) {

        // Validar que el trip existe
        var trip = tripRepository.findById(request.tripId())
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + request.tripId()));

        // Validar que el driver existe y tiene el rol correcto
        var driver = userRepository.findById(request.driverId())
                .orElseThrow(() -> new NotFoundException("Driver not found with id: " + request.driverId()));

        if (driver.getRole() != Role.DRIVER) {
            throw new IllegalArgumentException("User with id " + request.driverId() + " is not a driver");
        }

        // Validar que el dispatcher existe y tiene el rol correcto
        var dispatcher = userRepository.findById(request.dispatcherId())
                .orElseThrow(() -> new NotFoundException("Dispatcher not found with id: " + request.dispatcherId()));

        if (dispatcher.getRole() != Role.DISPATCHER) {
            throw new IllegalArgumentException("User with id " + request.dispatcherId() + " is not a dispatcher");
        }

        // Validar que no exista ya un assignment para este trip
        var existingAssignment = assignmentRepository.findByTripId(request.tripId());
        if (existingAssignment.isPresent()) {
            throw new IllegalStateException("Trip " + request.tripId() + " already has an assignment");
        }

        var assignment = assignmentMapper.toEntity(request);
        assignment.setTrip(trip);
        assignment.setDriver(driver);
        assignment.setDispatcher(dispatcher);
        assignment.setAssignedAt(LocalDateTime.now());

        var savedAssignment = assignmentRepository.save(assignment);

        return assignmentMapper.toResponse(savedAssignment);
    }

    @Override
    @Transactional(readOnly = true)
    public AssignmentResponse getById(Long id) {
        return assignmentRepository.findById(id)
                .map(assignmentMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Assignment not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public AssignmentResponse getByTripId(Long tripId) {
        return assignmentRepository.findByTripId(tripId)
                .map(assignmentMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Assignment not found for trip: " + tripId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentResponse> getAll() {
        return assignmentMapper.toResponseList(assignmentRepository.findAll());
    }

    @Override
    public AssignmentResponse update(Long id, AssignmentUpdateRequest request) {

        var assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assignment not found with id: " + id));

        // Si se actualiza el driver, validar que existe y tiene el rol correcto
        if (request.driverId() != null) {
            var driver = userRepository.findById(request.driverId())
                    .orElseThrow(() -> new NotFoundException("Driver not found with id: " + request.driverId()));

            if (driver.getRole() != Role.DRIVER) {
                throw new IllegalArgumentException("User with id " + request.driverId() + " is not a driver");
            }

            assignment.setDriver(driver);
        }

        assignmentMapper.updateEntityFromDto(request, assignment);
        var updatedAssignment = assignmentRepository.save(assignment);

        return assignmentMapper.toResponse(updatedAssignment);
    }

    @Override
    public void delete(Long id) {

        if (!assignmentRepository.existsById(id)) {
            throw new NotFoundException("Assignment not found with id: " + id);
        }

        assignmentRepository.deleteById(id);
    }
}



