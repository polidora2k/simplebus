package com.polidoraian.simplebus.shared.service;

import com.polidoraian.simplebus.shared.dto.RouteDTO;

import java.util.List;

public interface RouteService {
    RouteDTO getRoute(Integer id);
    void advanceStop(Integer routeId, Integer previousStopId);
    void completeRoute(Integer routeId);
    void startRoute(Integer routeId);
    RouteDTO addRoute(String routeName);
    Boolean resetRoutes();
    List<RouteDTO> getAllRoutes();
}
