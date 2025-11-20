package com.proyecto.Proyecto.Reservas.services;

import com.proyecto.Proyecto.Reservas.api.dto.BusDtos.*;
import java.util.List;

public interface BusService {
    BusResponse create(BusCreateRequest request);
    BusResponse getById(Long id);
    BusResponse getByPlate(String plate);
    List<BusResponse> getAll();
    BusResponse update(Long id, BusUpdateRequest request);
    void delete(Long id);
}



