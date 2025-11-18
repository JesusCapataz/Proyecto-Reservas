package com.proyecto.Proyecto.Reservas.domain.repositories;

import com.proyecto.Proyecto.Reservas.domain.entities.FareRule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FareRuleRepository extends JpaRepository<FareRule, Long> {
    List<FareRule> findByRouteId(Long routeId);
}