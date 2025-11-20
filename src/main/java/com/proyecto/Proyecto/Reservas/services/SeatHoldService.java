package com.proyecto.Proyecto.Reservas.services;

import com.proyecto.Proyecto.Reservas.api.dto.SeatHoldDtos.*;
import java.util.List;

public interface SeatHoldService {
    SeatHoldResponse holdSeat(SeatHoldCreateRequest request);
    SeatHoldResponse getById(Long id);
    List<SeatHoldResponse> getByTripId(Long tripId);
    SeatAvailabilityResponse checkSeatAvailability(Long tripId, Integer seatNumber);
    void expireHolds();
    void releaseHold(Long holdId);
}