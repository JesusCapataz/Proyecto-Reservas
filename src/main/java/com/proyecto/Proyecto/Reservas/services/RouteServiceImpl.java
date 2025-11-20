package com.proyecto.Proyecto.Reservas.services;

import com.proyecto.Proyecto.Reservas.api.dto.RouteDtos.*;
import com.proyecto.Proyecto.Reservas.domain.entities.Route;
import com.proyecto.Proyecto.Reservas.domain.repositories.RouteRepository;
import com.proyecto.Proyecto.Reservas.domain.repositories.StopRepository;
import com.proyecto.Proyecto.Reservas.exception.NotFoundException;
import com.proyecto.Proyecto.Reservas.services.mapper.RouteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;
    private final RouteMapper routeMapper;

    @Override
    public RouteResponse create(RouteCreateRequest request) {
        log.info("Creating route with code: {}", request.code());

        // Validar que el código no exista
        if (routeRepository.findByCode(request.code()).isPresent()) {
            throw new IllegalArgumentException("Route code already exists: " + request.code());
        }

        var route = routeMapper.toEntity(request);
        route.setStops(new ArrayList<>());

        // Crear las paradas si se proporcionaron
        if (request.stops() != null && !request.stops().isEmpty()) {
            for (StopCreateRequest stopRequest : request.stops()) {
                var stop = routeMapper.toStopEntity(stopRequest);
                route.addStop(stop);
            }
        }

        var savedRoute = routeRepository.save(route);
        log.info("Route created with id: {}", savedRoute.getId());

        return routeMapper.toResponse(savedRoute);
    }

    @Override
    @Transactional(readOnly = true)
    public RouteResponse getById(Long id) {
        log.info("Getting route by id: {}", id);
        return routeRepository.findById(id)
                .map(routeMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Route not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public RouteResponse getByCode(String code) {
        log.info("Getting route by code: {}", code);
        return routeRepository.findByCode(code)
                .map(routeMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Route not found with code: " + code));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RouteResponse> getAll() {
        log.info("Getting all routes");
        return routeMapper.toResponseList(routeRepository.findAll());
    }

    @Override
    public RouteResponse update(Long id, RouteUpdateRequest request) {
        log.info("Updating route with id: {}", id);

        var route = routeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Route not found with id: " + id));

        routeMapper.updateEntityFromDto(request, route);
        var updatedRoute = routeRepository.save(route);

        log.info("Route updated with id: {}", updatedRoute.getId());
        return routeMapper.toResponse(updatedRoute);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting route with id: {}", id);

        if (!routeRepository.existsById(id)) {
            throw new NotFoundException("Route not found with id: " + id);
        }

        routeRepository.deleteById(id);
        log.info("Route deleted with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StopResponse> getStopsByRouteId(Long routeId) {
        log.info("Getting stops for route id: {}", routeId);

        // Validar que la ruta existe
        if (!routeRepository.existsById(routeId)) {
            throw new NotFoundException("Route not found with id: " + routeId);
        }

        var stops = stopRepository.findByRouteIdOrderByOrderIndexAsc(routeId);
        return routeMapper.toStopResponseList(stops);
    }
}



