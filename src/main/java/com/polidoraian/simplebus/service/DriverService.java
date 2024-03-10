package com.polidoraian.simplebus.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.polidoraian.simplebus.database.dao.DriverRouteDAO;
import com.polidoraian.simplebus.database.dao.UserDAO;
import com.polidoraian.simplebus.database.entity.DriverRoute;
import com.polidoraian.simplebus.database.entity.User;
import com.polidoraian.simplebus.dto.RouteDTO;
import com.polidoraian.simplebus.dto.UserDTO;
import com.polidoraian.simplebus.dto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DriverService {
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private DriverRouteDAO driverRouteDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserMapper userMapper;

	public List<RouteDTO> getRoutesForDriver(Integer driverId) {
		List<DriverRoute> driverRoutes = driverRouteDAO.findIncompleteRoutesByDriver(driverId);
		List<RouteDTO> routes = driverRoutes.stream().map(dr -> routeService.getRoute(dr.getUserId())).collect(Collectors.toList());

		return routes;
	}
	
	public UserDTO getDriverForRoute(Integer routeId) {
		Optional<DriverRoute> dr = driverRouteDAO.findByRouteId(routeId);
		
		if (dr.isPresent()) {
			Optional<User> driver = userDAO.findById(dr.get().getUserId());
			if (driver.isPresent()) {
				return userMapper.toUserDTO(driver.get());
			}
		}
		
		return null;
	}
}
