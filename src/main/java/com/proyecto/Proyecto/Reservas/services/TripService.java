package com.proyecto.Proyecto.Reservas.services;


import com.proyecto.Proyecto.Reservas.api.dto.TripDtos.*;
import java.time.LocalDate;
import java.util.List;

public interface TripService {
    TripResponse create(TripCreateRequest request);
    TripResponse getById(Long id);
    List<TripResponse> getAll();
    List<TripResponse> searchTrips(Long routeId, LocalDate date);
    TripResponse update(Long id, TripUpdateRequest request);
    void delete(Long id);
}



