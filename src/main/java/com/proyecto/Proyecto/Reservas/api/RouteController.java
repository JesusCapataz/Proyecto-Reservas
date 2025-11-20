package com.proyecto.Proyecto.Reservas.api;


import com.proyecto.Proyecto.Reservas.api.dto.RouteDtos.*;
import com.proyecto.Proyecto.Reservas.services.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(@Valid @RequestBody RouteCreateRequest request) {
        log.info("POST /api/routes - Creating route with code: {}", request.code());
        var response = routeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RouteResponse>> getAllRoutes() {
        log.info("GET /api/routes - Getting all routes");
        var response = routeService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable Long id) {
        log.info("GET /api/routes/{} - Getting route by id", id);
        var response = routeService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/stops")
    public ResponseEntity<List<StopResponse>> getStopsByRoute(@PathVariable Long id) {
        log.info("GET /api/routes/{}/stops - Getting stops for route", id);
        var response = routeService.getStopsByRouteId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<RouteResponse> getRouteByCode(@PathVariable String code) {
        log.info("GET /api/routes/code/{} - Getting route by code", code);
        var response = routeService.getByCode(code);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteResponse> updateRoute(
            @PathVariable Long id,
            @Valid @RequestBody RouteUpdateRequest request) {
        log.info("PUT /api/routes/{} - Updating route", id);
        var response = routeService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        log.info("DELETE /api/routes/{} - Deleting route", id);
        routeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



