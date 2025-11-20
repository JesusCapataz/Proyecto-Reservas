package com.proyecto.Proyecto.Reservas.security.service;

import com.proyecto.Proyecto.Reservas.api.dto.AuthDtos.*;
import com.proyecto.Proyecto.Reservas.domain.repositories.UserRepository;
import com.proyecto.Proyecto.Reservas.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest request) {


        // Autenticar al usuario
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // Cargar detalles del usuario
        var userDetails = userDetailsService.loadUserByUsername(request.email());

        // Obtener información adicional del usuario
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generar token JWT con claims adicionales
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("name", user.getName());

        String token = jwtService.generateToken(userDetails, extraClaims);



        return new LoginResponse(
                token,
                "Bearer",
                jwtService.getExpirationSeconds(),
                new UserInfo(user.getId(), user.getName(), user.getEmail(), user.getRole().name())
        );
    }
}