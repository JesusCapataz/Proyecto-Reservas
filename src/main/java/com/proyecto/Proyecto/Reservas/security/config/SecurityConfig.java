package com.proyecto.Proyecto.Reservas.security.config;

import com.proyecto.Proyecto.Reservas.security.error.Http401EntryPoint;
import com.proyecto.Proyecto.Reservas.security.error.Http403AccessDenied;
import com.proyecto.Proyecto.Reservas.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity // Para usar @PreAuthorize en los controllers
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final Http401EntryPoint authEntryPoint;
    private final Http403AccessDenied accessDenied;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(accessDenied))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users/register").permitAll()

                        // Búsqueda de rutas y viajes - público
                        .requestMatchers(HttpMethod.GET, "/api/routes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/trips").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/trips/{id}").permitAll()

                        // Tracking de encomiendas - público
                        .requestMatchers(HttpMethod.GET, "/api/parcels/*/track").permitAll()

                        // Verificación de disponibilidad de asientos - público
                        .requestMatchers(HttpMethod.GET, "/api/seat-holds/trips/*/seats/*/availability").permitAll()

                        // ADMIN - puede hacer DELETE en cualquier endpoint
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")

                        // ADMIN - gestión de rutas y buses
                        .requestMatchers(HttpMethod.POST, "/api/routes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/routes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/buses").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/buses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/buses/**").hasAnyRole("ADMIN", "DISPATCHER")

                        // DISPATCHER - gestión de viajes y asignaciones
                        .requestMatchers(HttpMethod.POST, "/api/trips").hasAnyRole("ADMIN", "DISPATCHER")
                        .requestMatchers(HttpMethod.PUT, "/api/trips/**").hasAnyRole("ADMIN", "DISPATCHER", "PASSENGER")
                        .requestMatchers("/api/assignments/**").hasRole("DISPATCHER")
                        .requestMatchers("/api/trips/*/boarding/**").hasRole("DISPATCHER")
                        .requestMatchers(HttpMethod.POST, "/api/trips/*/cancel").hasAnyRole("DISPATCHER", "ADMIN")

                        // DRIVER - operaciones de viaje
                        .requestMatchers(HttpMethod.POST, "/api/trips/*/depart").hasAnyRole("DRIVER", "DISPATCHER")
                        .requestMatchers(HttpMethod.POST, "/api/trips/*/arrive").hasRole("DRIVER")
                        .requestMatchers(HttpMethod.GET, "/api/tickets/qr/**").hasAnyRole("DRIVER", "CLERK")
                        .requestMatchers(HttpMethod.PUT, "/api/tickets/**").hasAnyRole("CLERK", "DRIVER")

                        // CLERK - venta de tickets y encomiendas
                        .requestMatchers(HttpMethod.POST, "/api/tickets").hasAnyRole("PASSENGER", "CLERK")
                        .requestMatchers(HttpMethod.POST, "/api/tickets/*/cancel").hasAnyRole("PASSENGER", "CLERK")
                        .requestMatchers(HttpMethod.POST, "/api/seat-holds/**").hasAnyRole("PASSENGER", "CLERK")
                        .requestMatchers(HttpMethod.DELETE, "/api/seat-holds/**").hasAnyRole("PASSENGER", "CLERK")
                        .requestMatchers(HttpMethod.POST, "/api/parcels").hasRole("CLERK")
                        .requestMatchers(HttpMethod.PUT, "/api/parcels/**").hasAnyRole("CLERK", "DRIVER")
                        .requestMatchers(HttpMethod.POST, "/api/parcels/*/deliver").hasAnyRole("CLERK", "DRIVER")
                        .requestMatchers(HttpMethod.GET, "/api/parcels/**").hasRole("CLERK")

                        // PASSENGER - consultas de sus propios tickets
                        .requestMatchers(HttpMethod.GET, "/api/tickets/passengers/**").hasRole("PASSENGER")

                        // Cualquier otro endpoint requiere autenticación
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}