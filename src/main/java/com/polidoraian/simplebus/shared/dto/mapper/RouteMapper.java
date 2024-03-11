package com.polidoraian.simplebus.shared.dto.mapper;

import com.polidoraian.simplebus.shared.database.entity.Route;
import com.polidoraian.simplebus.shared.dto.RouteDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RouteMapper {
    @Mapping(target = "stops", ignore = true)
    Route dtoToEntity(RouteDTO routeDTO);
    
    RouteDTO entityToDto(Route route);
}
