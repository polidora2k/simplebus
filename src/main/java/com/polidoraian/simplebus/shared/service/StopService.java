package com.polidoraian.simplebus.shared.service;

import com.google.maps.model.LatLng;
import com.polidoraian.simplebus.shared.dto.StopCreationDTO;
import com.polidoraian.simplebus.shared.dto.StopDTO;

import java.util.List;

public interface StopService {
    StopDTO getStop(Integer id);
    List<StopDTO> getRouteStops(Integer routeId);
    List<StopDTO> getRouteStopsWithStudents(Integer routeId);
    StopDTO getRouteStopByNumber(Integer routeId, Integer stopNumber);
    List<StopDTO> getUnassignedStops();
    void addStop(StopCreationDTO stopCreationDTO, Integer routeId);
    List<LatLng> getStopLocations(List<StopDTO> stops);
    LatLng getStopLocation(StopDTO stop);
    void completeStop(Integer stopId);
    void resetStopsForRoute(Integer routeId);
}
