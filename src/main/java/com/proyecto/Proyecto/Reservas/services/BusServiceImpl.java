package com.proyecto.Proyecto.Reservas.services;


import com.proyecto.Proyecto.Reservas.api.dto.BusDtos.*;
import com.proyecto.Proyecto.Reservas.domain.repositories.BusRepository;
import com.proyecto.Proyecto.Reservas.exception.NotFoundException;
import com.proyecto.Proyecto.Reservas.services.mapper.BusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final BusMapper busMapper;

    @Override
    public BusResponse create(BusCreateRequest request) {

        // Validar que la placa no exista
        if (busRepository.findByPlate(request.plate()).isPresent()) {
            throw new IllegalArgumentException("Bus plate already exists: " + request.plate());
        }

        // Validar que la cantidad de asientos coincida con la capacidad
        if (request.seats() != null && request.seats().size() != request.capacity()) {
            throw new IllegalArgumentException(
                    "Number of seats (" + request.seats().size() + ") must match capacity (" + request.capacity() + ")"
            );
        }

        var bus = busMapper.toEntity(request);
        bus.setSeats(new ArrayList<>());

        // Crear los asientos
        if (request.seats() != null) {
            for (SeatCreateRequest seatRequest : request.seats()) {
                var seat = busMapper.toSeatEntity(seatRequest);
                bus.addSeat(seat);
            }
        }

        var savedBus = busRepository.save(bus);

        return busMapper.toResponse(savedBus);
    }

    @Override
    @Transactional(readOnly = true)
    public BusResponse getById(Long id) {
        return busRepository.findById(id)
                .map(busMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Bus not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public BusResponse getByPlate(String plate) {
        return busRepository.findByPlate(plate)
                .map(busMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Bus not found with plate: " + plate));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusResponse> getAll() {
        return busMapper.toResponseList(busRepository.findAll());
    }

    @Override
    public BusResponse update(Long id, BusUpdateRequest request) {

        var bus = busRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bus not found with id: " + id));

        // Validar placa única si se está actualizando
        if (request.plate() != null && !request.plate().equals(bus.getPlate())) {
            if (busRepository.findByPlate(request.plate()).isPresent()) {
                throw new IllegalArgumentException("Bus plate already exists: " + request.plate());
            }
        }

        busMapper.updateEntityFromDto(request, bus);
        var updatedBus = busRepository.save(bus);

        return busMapper.toResponse(updatedBus);
    }

    @Override
    public void delete(Long id) {

        if (!busRepository.existsById(id)) {
            throw new NotFoundException("Bus not found with id: " + id);
        }

        busRepository.deleteById(id);
    }
}



