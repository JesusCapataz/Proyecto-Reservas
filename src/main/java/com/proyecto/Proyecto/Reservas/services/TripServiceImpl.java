package com.proyecto.Proyecto.Reservas.services;


import com.proyecto.Proyecto.Reservas.api.dto.TripDtos.*;
import com.proyecto.Proyecto.Reservas.domain.repositories.BusRepository;
import com.proyecto.Proyecto.Reservas.domain.repositories.RouteRepository;
import com.proyecto.Proyecto.Reservas.domain.repositories.TripRepository;
import com.proyecto.Proyecto.Reservas.exception.NotFoundException;
import com.proyecto.Proyecto.Reservas.services.mapper.TripMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;
    private final TripMapper tripMapper;

    @Override
    public TripResponse create(TripCreateRequest request) {

        // Validar que la ruta existe
        var route = routeRepository.findById(request.routeId())
                .orElseThrow(() -> new NotFoundException("Route not found with id: " + request.routeId()));

        // Validar que el bus existe
        var bus = busRepository.findById(request.busId())
                .orElseThrow(() -> new NotFoundException("Bus not found with id: " + request.busId()));

        // Validar que la fecha de salida sea antes de la llegada
        if (request.departureAt().isAfter(request.arrivalEta())) {
            throw new IllegalArgumentException("Departure time must be before arrival time");
        }

        var trip = tripMapper.toEntity(request);
        trip.setRoute(route);
        trip.setBus(bus);

        var savedTrip = tripRepository.save(trip);

        return tripMapper.toResponse(savedTrip);
    }

    @Override
    @Transactional(readOnly = true)
    public TripResponse getById(Long id) {
        return tripRepository.findById(id)
                .map(tripMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripResponse> getAll() {
        return tripMapper.toResponseList(tripRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripResponse> searchTrips(Long routeId, LocalDate date) {

        if (routeId != null && date != null) {
            var trips = tripRepository.findByRouteIdAndDate(routeId, date);
            return tripMapper.toResponseList(trips);
        } else if (routeId != null) {
            var route = routeRepository.findById(routeId)
                    .orElseThrow(() -> new NotFoundException("Route not found with id: " + routeId));
            return tripMapper.toResponseList(tripRepository.findAll()
                    .stream()
                    .filter(t -> t.getRoute().getId().equals(routeId))
                    .toList());
        } else if (date != null) {
            var trips = tripRepository.findAll()
                    .stream()
                    .filter(t -> t.getDate().equals(date))
                    .toList();
            return tripMapper.toResponseList(trips);
        }

        return getAll();
    }

    @Override
    public TripResponse update(Long id, TripUpdateRequest request) {

        var trip = tripRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + id));

        // Validar que si se actualiza la fecha de salida y llegada, la salida sea antes
        if (request.departureAt() != null && request.arrivalEta() != null) {
            if (request.departureAt().isAfter(request.arrivalEta())) {
                throw new IllegalArgumentException("Departure time must be before arrival time");
            }
        }

        tripMapper.updateEntityFromDto(request, trip);
        var updatedTrip = tripRepository.save(trip);

        return tripMapper.toResponse(updatedTrip);
    }

    @Override
    public void delete(Long id) {

        if (!tripRepository.existsById(id)) {
            throw new NotFoundException("Trip not found with id: " + id);
        }

        tripRepository.deleteById(id);
    }
}



