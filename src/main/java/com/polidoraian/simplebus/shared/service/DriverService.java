package com.polidoraian.simplebus.shared.service;

import com.polidoraian.simplebus.shared.dto.RouteDTO;
import com.polidoraian.simplebus.shared.dto.UserDTO;

import java.util.List;

public interface DriverService {
    List<RouteDTO> getRoutesForDriver(Integer driverId);
    UserDTO getDriverForRoute(Integer routeId);
}
