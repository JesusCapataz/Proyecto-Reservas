package com.proyecto.Proyecto.Reservas.services;

import com.proyecto.Proyecto.Reservas.api.dto.RouteDtos.*;
import java.util.List;

public interface RouteService {
    RouteResponse create(RouteCreateRequest request);
    RouteResponse getById(Long id);
    RouteResponse getByCode(String code);
    List<RouteResponse> getAll();
    RouteResponse update(Long id, RouteUpdateRequest request);
    void delete(Long id);
    List<StopResponse> getStopsByRouteId(Long routeId);
}