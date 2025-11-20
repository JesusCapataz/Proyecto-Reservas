package com.proyecto.Proyecto.Reservas.services;


import com.proyecto.Proyecto.Reservas.api.dto.UserDtos.*;
import com.proyecto.Proyecto.Reservas.domain.repositories.UserRepository;
import com.proyecto.Proyecto.Reservas.exception.NotFoundException;
import com.proyecto.Proyecto.Reservas.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse create(UserCreateRequest request) {
        log.info("Creating user with email: {}", request.email());

        // Validar que el email no exista
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + request.email());
        }

        var user = userMapper.toEntity(request);
        // TODO: Aquí deberías encriptar la contraseña con BCrypt
        user.setPasswordHash(request.password()); // Por ahora guardamos directamente

        var savedUser = userRepository.save(user);
        log.info("User created with id: {}", savedUser.getId());

        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        log.info("Getting user by id: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getByEmail(String email) {
        log.info("Getting user by email: {}", email);
        return userRepository.findByEmail(email)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        log.info("Getting all users");
        return userMapper.toResponseList(userRepository.findAll());
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest request) {
        log.info("Updating user with id: {}", id);

        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        // Validar email único si se está actualizando
        if (request.email() != null && !request.email().equals(user.getEmail())) {
            if (userRepository.findByEmail(request.email()).isPresent()) {
                throw new IllegalArgumentException("Email already exists: " + request.email());
            }
        }

        userMapper.updateEntityFromDto(request, user);
        var updatedUser = userRepository.save(user);

        log.info("User updated with id: {}", updatedUser.getId());
        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user with id: {}", id);

        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
        log.info("User deleted with id: {}", id);
    }
}



