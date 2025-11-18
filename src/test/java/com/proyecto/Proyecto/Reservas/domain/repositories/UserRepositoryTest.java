package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.User;
import com.proyecto.Proyecto.Reservas.domain.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldFindUserByEmail() {
        // Given
        User user = User.builder()
                .name("Pepito Perez")
                .email("pepito@test.com")
                .passwordHash("123456")
                .role(Role.PASSENGER)
                .status(true)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        // When
        Optional<User> found = userRepository.findByEmail("pepito@test.com");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Pepito Perez");
        assertThat(found.get().getRole()).isEqualTo(Role.PASSENGER);
    }
}