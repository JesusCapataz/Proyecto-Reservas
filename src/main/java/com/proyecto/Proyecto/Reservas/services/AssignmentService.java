package com.proyecto.Proyecto.Reservas.services;

import com.proyecto.Proyecto.Reservas.api.dto.AssignmentDtos.*;
import java.util.List;

public interface AssignmentService {
    AssignmentResponse create(AssignmentCreateRequest request);
    AssignmentResponse getById(Long id);
    AssignmentResponse getByTripId(Long tripId);
    List<AssignmentResponse> getAll();
    AssignmentResponse update(Long id, AssignmentUpdateRequest request);
    void delete(Long id);
}