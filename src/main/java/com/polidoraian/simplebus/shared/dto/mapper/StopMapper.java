package com.polidoraian.simplebus.shared.dto.mapper;

import com.polidoraian.simplebus.shared.entity.Stop;
import com.polidoraian.simplebus.shared.dto.StopCreationDTO;
import com.polidoraian.simplebus.shared.dto.StopDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StopMapper {
    Stop dtoToEntity(StopDTO stopDTO);
    
    StopDTO entityToDto(Stop stop);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "routeId", ignore = true)
    @Mapping(target = "routeStopNumber", ignore = true)
    Stop creationDtoToEntity(StopCreationDTO stopCreationDTO);
}
