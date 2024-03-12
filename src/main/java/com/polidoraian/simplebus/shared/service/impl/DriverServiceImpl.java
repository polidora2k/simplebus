package com.polidoraian.simplebus.shared.service.impl;

import com.polidoraian.simplebus.shared.dto.RouteDTO;
import com.polidoraian.simplebus.shared.dto.UserDTO;
import com.polidoraian.simplebus.shared.dto.mapper.UserMapper;
import com.polidoraian.simplebus.shared.entity.DriverRoute;
import com.polidoraian.simplebus.shared.entity.User;
import com.polidoraian.simplebus.shared.repository.DriverRouteRepository;
import com.polidoraian.simplebus.shared.repository.UserRepository;
import com.polidoraian.simplebus.shared.service.DriverService;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DriverServiceImpl implements DriverService {
	private final RouteServiceImpl routeService;
	private final DriverRouteRepository driverRouteRepository;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	
	public DriverServiceImpl(@Nonnull final RouteServiceImpl routeService,
							 @Nonnull final DriverRouteRepository driverRouteRepository,
							 @Nonnull final UserRepository userRepository,
							 @Nonnull final UserMapper userMapper) {
		this.routeService = routeService;
		this.driverRouteRepository = driverRouteRepository;
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}
	
	public List<RouteDTO> getRoutesForDriver(Integer driverId) {
		List<DriverRoute> driverRoutes = driverRouteRepository.findIncompleteRoutesByDriver(driverId);
        
        return driverRoutes.stream().map(dr -> routeService.getRoute(dr.getUserId())).collect(Collectors.toList());
	}
	
	public UserDTO getDriverForRoute(Integer routeId) {
		Optional<DriverRoute> dr = driverRouteRepository.findByRouteId(routeId);
		
		if (dr.isPresent()) {
			Optional<User> driver = userRepository.findById(dr.get().getUserId());
			if (driver.isPresent()) {
				return userMapper.entityToDto(driver.get());
			}
		}
		
		return null;
	}
}
