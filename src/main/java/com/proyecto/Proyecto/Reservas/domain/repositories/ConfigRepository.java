package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ConfigRepository extends JpaRepository<Config, String> {
    Optional<Config> findByKeyConfig(String key);
}