package com.proyecto.Proyecto.Reservas.services;

import com.proyecto.Proyecto.Reservas.api.dto.UserDtos.*;
import java.util.List;

public interface UserService {
    UserResponse create(UserCreateRequest request);
    UserResponse getById(Long id);
    UserResponse getByEmail(String email);
    List<UserResponse> getAll();
    UserResponse update(Long id, UserUpdateRequest request);
    void delete(Long id);
}