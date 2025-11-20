package com.proyecto.Proyecto.Reservas.services;

import com.proyecto.Proyecto.Reservas.api.dto.TicketDtos.*;
import java.util.List;

public interface TicketService {
    TicketResponse createTicket(TicketCreateRequest request);
    TicketResponse getById(Long id);
    TicketResponse getByQrCode(String qrCode);
    List<TicketResponse> getByTripId(Long tripId);
    List<TicketResponse> getByPassengerId(Long passengerId);
    TicketResponse updateStatus(Long id, TicketUpdateRequest request);
    TicketResponse cancelTicket(Long id);
    void delete(Long id);
}
