package com.polidoraian.simplebus.shared.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.polidoraian.simplebus.shared.database.dao.StopDAO;
import com.polidoraian.simplebus.shared.database.dao.StudentDAO;
import com.polidoraian.simplebus.shared.database.entity.Stop;
import com.polidoraian.simplebus.shared.dto.StopCreationDTO;
import com.polidoraian.simplebus.shared.dto.StopDTO;
import com.polidoraian.simplebus.shared.dto.mapper.StopMapper;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.LatLng;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StopService {
    private final StopDAO stopDAO;
    private final StopMapper stopMapper;
	private final MapsService mapsService;
	private final StudentDAO studentDAO;
	
	@Autowired
	public StopService(@Nonnull final StopDAO stopDAO,
					   @Nonnull final StopMapper stopMapper,
					   @Nonnull final MapsService mapsService,
					   @Nonnull final StudentDAO studentDAO) {
		this.stopDAO = stopDAO;
		this.stopMapper = stopMapper;
		this.mapsService = mapsService;
		this.studentDAO = studentDAO;
	}
	
	public StopDTO getStop(Integer id) {
		Optional<Stop> stop = stopDAO.findById(id);
        
        return stop.map(stopMapper::entityToDto).orElse(null);
	}

	public List<StopDTO> getRouteStops(Integer routeId) {
		List<Stop> stops = stopDAO.findByRouteIdOrderByRouteStopNumberAsc(routeId);

		return stops.stream().map(stopMapper::entityToDto).collect(Collectors.toList());
	}
	
	public List<StopDTO> getRouteStopsWithStudents(Integer routeId) {
		List<Stop> stops = stopDAO.findByRouteIdOrderByRouteStopNumberAsc(routeId);
		List<StopDTO> stopDTOs = new ArrayList<>();
		
		for (Stop stop : stops) {
			if (studentDAO.countByStopIdAndRiding(stop.getId(), true) > 0) {
				stopDTOs.add(stopMapper.entityToDto(stop));
			}
		}

		return stopDTOs;
	}
	
	public StopDTO getRouteStopByNumber(Integer routeId, Integer stopNumber) {
		Optional<Stop> stop = stopDAO.findByRouteIdAndRouteStopNumber(routeId, stopNumber);
        
        return stop.map(stopMapper::entityToDto).orElse(null);
        
    }

	public List<StopDTO> getUnassignedStops() {
		List<Stop> stops = stopDAO.findByRouteId(null);

		return stops.stream().map(stopMapper::entityToDto).collect(Collectors.toList());
	}

	public StopDTO addStop(StopCreationDTO stopCreationDTO, Integer routeId) {
		Stop newStop = stopMapper.creationDtoToEntity(stopCreationDTO);
		
		newStop.setStatus("Incomplete");
		newStop.setRouteId(routeId);

		stopDAO.save(newStop);

		return stopMapper.entityToDto(newStop);
	}

	public List<LatLng> getStopLocations(List<StopDTO> stops) {
		List<LatLng> locations = new ArrayList<>();

		for (StopDTO stop : stops) {
			String address = stop.getStreetAddress() + " " + stop.getCity() + ", " + stop.getState() + " "
					+ stop.getZipcode();
			locations.add(mapsService.geocodeAddress(address));
			log.debug(address);
		}

		return locations;
	}
	
	public LatLng getStopLocation(StopDTO stop) {
		String address = stop.getStreetAddress() + " " + stop.getCity() + ", " + stop.getState() + " "
				+ stop.getZipcode();
		LatLng location = mapsService.geocodeAddress(address);
		log.debug(address);
			
		return location;
	}
	
	public void completeStop(Integer stopId) {
		Optional<Stop> stop = stopDAO.findById(stopId);
		
		if (stop.isPresent()) {
			Stop s = stop.get();
			s.setStatus("Completed");
			stopDAO.save(s);
		}
	}
	
	public void resetStopsForRoute(Integer routeId) {
		List<Stop> stops = stopDAO.findByRouteId(routeId);
		
		for (Stop s : stops) {
			s.setStatus("Incomplete");
			stopDAO.save(s);
		}
	}
}
