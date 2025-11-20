package com.proyecto.Proyecto.Reservas.services.mapper;


import com.proyecto.Proyecto.Reservas.api.dto.FareRuleDtos.*;
import com.proyecto.Proyecto.Reservas.domain.entities.FareRule;
import com.proyecto.Proyecto.Reservas.domain.entities.Stop;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FareRuleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "fromStop", ignore = true)
    @Mapping(target = "toStop", ignore = true)
    FareRule toEntity(FareRuleCreateRequest request);

    @Mapping(target = "routeId", source = "route.id")
    @Mapping(target = "fromStop", source = "fromStop")
    @Mapping(target = "toStop", source = "toStop")
    FareRuleResponse toResponse(FareRule fareRule);

    List<FareRuleResponse> toResponseList(List<FareRule> fareRules);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    StopInfo toStopInfo(Stop stop);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "fromStop", ignore = true)
    @Mapping(target = "toStop", ignore = true)
    void updateEntityFromDto(FareRuleUpdateRequest dto, @MappingTarget FareRule entity);
}



