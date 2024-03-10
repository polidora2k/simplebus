package com.polidoraian.simplebus.dto.mapper;

import com.polidoraian.simplebus.database.entity.Route;
import com.polidoraian.simplebus.dto.RouteDTO;
import com.polidoraian.simplebus.service.StopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper {
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private StopService stopService;
	
	public RouteDTO toRouteDTO(Route route) {
		RouteDTO routeDTO = mapper.map(route, RouteDTO.class);
		
		return routeDTO;
	}

	public Route toRoute(RouteDTO routeDTO) {
		return mapper.map(routeDTO, Route.class);
	}
}
