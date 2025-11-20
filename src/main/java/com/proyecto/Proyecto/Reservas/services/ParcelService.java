package com.proyecto.Proyecto.Reservas.services;

import com.proyecto.Proyecto.Reservas.api.dto.ParcelDtos.*;
import java.util.List;

public interface ParcelService {
    ParcelResponse create(ParcelCreateRequest request);
    ParcelResponse getById(Long id);
    ParcelResponse getByCode(String code);
    List<ParcelResponse> getAll();
    ParcelResponse update(Long id, ParcelUpdateRequest request);
    ParcelResponse deliverParcel(Long id, ParcelDeliveryRequest request);
    ParcelTrackingResponse trackParcel(String code);
    void delete(Long id);
}