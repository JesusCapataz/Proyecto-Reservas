package com.proyecto.Proyecto.Reservas.services;


import com.proyecto.Proyecto.Reservas.api.dto.ParcelDtos.*;
import com.proyecto.Proyecto.Reservas.domain.enums.ParcelStatus;
import com.proyecto.Proyecto.Reservas.domain.repositories.ParcelRepository;
import com.proyecto.Proyecto.Reservas.domain.repositories.StopRepository;
import com.proyecto.Proyecto.Reservas.domain.repositories.TripRepository;
import com.proyecto.Proyecto.Reservas.exception.NotFoundException;
import com.proyecto.Proyecto.Reservas.services.mapper.ParcelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;
    private final TripRepository tripRepository;
    private final StopRepository stopRepository;
    private final ParcelMapper parcelMapper;

    @Override
    public ParcelResponse create(ParcelCreateRequest request) {

        // Validar que el trip existe
        var trip = tripRepository.findById(request.tripId())
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + request.tripId()));

        // Validar las paradas
        var fromStop = stopRepository.findById(request.fromStopId())
                .orElseThrow(() -> new NotFoundException("From stop not found with id: " + request.fromStopId()));

        var toStop = stopRepository.findById(request.toStopId())
                .orElseThrow(() -> new NotFoundException("To stop not found with id: " + request.toStopId()));

        // Validar que las paradas pertenecen a la ruta del trip
        if (!fromStop.getRoute().getId().equals(trip.getRoute().getId()) ||
                !toStop.getRoute().getId().equals(trip.getRoute().getId())) {
            throw new IllegalArgumentException("Stops must belong to the trip's route");
        }

        // Validar que el orden de las paradas sea correcto
        if (fromStop.getOrderIndex() >= toStop.getOrderIndex()) {
            throw new IllegalArgumentException("From stop must be before to stop");
        }

        var parcel = parcelMapper.toEntity(request);
        parcel.setFromStop(fromStop);
        parcel.setToStop(toStop);
        parcel.setTrip(trip);
        parcel.setCode(generateParcelCode());
        parcel.setDeliveryOtp(generateOtp());

        var savedParcel = parcelRepository.save(parcel);

        return parcelMapper.toResponse(savedParcel);
    }

    @Override
    @Transactional(readOnly = true)
    public ParcelResponse getById(Long id) {
        return parcelRepository.findById(id)
                .map(parcelMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Parcel not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public ParcelResponse getByCode(String code) {
        return parcelRepository.findByCode(code)
                .map(parcelMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Parcel not found with code: " + code));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParcelResponse> getAll() {
        return parcelMapper.toResponseList(parcelRepository.findAll());
    }

    @Override
    public ParcelResponse update(Long id, ParcelUpdateRequest request) {

        var parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parcel not found with id: " + id));

        parcelMapper.updateEntityFromDto(request, parcel);
        var updatedParcel = parcelRepository.save(parcel);

        return parcelMapper.toResponse(updatedParcel);
    }

    @Override
    public ParcelResponse deliverParcel(Long id, ParcelDeliveryRequest request) {

        var parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parcel not found with id: " + id));

        // Validar el estado actual
        if (parcel.getStatus() != ParcelStatus.IN_TRANSIT) {
            throw new IllegalStateException("Parcel must be in transit to be delivered. Current status: " + parcel.getStatus());
        }

        // Validar el OTP
        if (!parcel.getDeliveryOtp().equals(request.otp())) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        // Actualizar el estado y la foto de prueba
        parcel.setStatus(ParcelStatus.DELIVERED);
        parcel.setProofPhotoUrl(request.proofPhotoUrl());

        var deliveredParcel = parcelRepository.save(parcel);

        return parcelMapper.toResponse(deliveredParcel);
    }

    @Override
    @Transactional(readOnly = true)
    public ParcelTrackingResponse trackParcel(String code) {

        var parcel = parcelRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Parcel not found with code: " + code));

        return parcelMapper.toTrackingResponse(parcel);
    }

    @Override
    public void delete(Long id) {

        if (!parcelRepository.existsById(id)) {
            throw new NotFoundException("Parcel not found with id: " + id);
        }

        parcelRepository.deleteById(id);
    }

    private String generateParcelCode() {
        return "PKG-" + System.currentTimeMillis() + "-" + new Random().nextInt(1000);
    }

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
}



