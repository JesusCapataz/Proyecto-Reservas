package com.proyecto.Proyecto.Reservas.api;


import com.proyecto.Proyecto.Reservas.api.dto.TicketDtos.*;
import com.proyecto.Proyecto.Reservas.services.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    /**
     * POST /api/trips/{tripId}/tickets - comprar ticket (validación de tramo y precio)
     */
    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody TicketCreateRequest request) {

        var response = ticketService.createTicket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id) {
        var response = ticketService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/qr/{qrCode}")
    public ResponseEntity<TicketResponse> getTicketByQrCode(@PathVariable String qrCode) {
        var response = ticketService.getByQrCode(qrCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trips/{tripId}")
    public ResponseEntity<List<TicketResponse>> getTicketsByTrip(@PathVariable Long tripId) {
        var response = ticketService.getByTripId(tripId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/passengers/{passengerId}")
    public ResponseEntity<List<TicketResponse>> getTicketsByPassenger(@PathVariable Long passengerId) {
        var response = ticketService.getByPassengerId(passengerId);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/tickets/{id}/cancel - cancelar ticket con política de reembolso
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<TicketResponse> cancelTicket(@PathVariable Long id) {
        var response = ticketService.cancelTicket(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> updateTicketStatus(
            @PathVariable Long id,
            @Valid @RequestBody TicketUpdateRequest request) {
        var response = ticketService.updateStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



