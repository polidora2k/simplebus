package com.polidoraian.simplebus.shared.service.impl;

import com.polidoraian.simplebus.shared.dto.RouteDTO;
import com.polidoraian.simplebus.shared.dto.mapper.RouteMapper;
import com.polidoraian.simplebus.shared.entity.Route;
import com.polidoraian.simplebus.shared.repository.RouteRepository;
import com.polidoraian.simplebus.shared.service.RouteService;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RouteServiceImpl implements RouteService {
	RouteRepository routeRepository;
    RouteMapper routeMapper;
	StopServiceImpl stopService;
	
	public RouteServiceImpl(@Nonnull final RouteRepository routeRepository,
							@Nonnull final RouteMapper routeMapper,
							@Nonnull final StopServiceImpl stopService) {
		this.routeRepository = routeRepository;
		this.routeMapper = routeMapper;
		this.stopService = stopService;
	}
	
	public RouteDTO getRoute(Integer id) {
		Optional<Route> route = routeRepository.findById(id);

		if (route.isPresent()) {
			return routeMapper.entityToDto(route.get());
		} else {
			log.warn("No user found for id: " + id);
			return null;
		}
	}

	public void advanceStop(Integer routeId, Integer previousStopId) {
		Optional<Route> route = routeRepository.findById(routeId);

		if (route.isPresent()) {
			Route r = route.get();
			r.setLastCompletedStopId(previousStopId);
			routeRepository.save(r);
		}
	}
	
	public void completeRoute(Integer routeId) {
		Optional<Route> route = routeRepository.findById(routeId);
		
		if (route.isPresent()) {
			Route r = route.get();
			r.setStatus("Completed");
			routeRepository.save(r);
		}
	}
	
	public void startRoute(Integer routeId) {
		Optional<Route> route = routeRepository.findById(routeId);
		
		if (route.isPresent()) {
			Route r = route.get();
			r.setStatus("In Progress");
			routeRepository.save(r);
		}
	}
	
	public RouteDTO addRoute(String routeName) {
		Route route = new Route();
		route.setName(routeName);
		route.setStatus("Not Departed");
		
		routeRepository.save(route);
		
		return routeMapper.entityToDto(route);
	}
	
	public Boolean resetRoutes() {
		List<Route> routes = routeRepository.findAll();
		
		for (Route r : routes) {
			r.setStatus("Not Departed");
			r.setLastCompletedStopId(null);
			routeRepository.save(r);
			
			stopService.resetStopsForRoute(r.getId());
		}
		
		return true;
	}
	
	public List<RouteDTO> getAllRoutes() {
		List<Route> routes = routeRepository.findAll();
		
		return routes.stream().map(routeMapper::entityToDto).collect(Collectors.toList());
	}
}
