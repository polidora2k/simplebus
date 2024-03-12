package com.polidoraian.simplebus.shared.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.polidoraian.simplebus.shared.repository.StopRepository;
import com.polidoraian.simplebus.shared.repository.StudentRepository;
import com.polidoraian.simplebus.shared.entity.Stop;
import com.polidoraian.simplebus.shared.dto.StopCreationDTO;
import com.polidoraian.simplebus.shared.dto.StopDTO;
import com.polidoraian.simplebus.shared.dto.mapper.StopMapper;
import com.polidoraian.simplebus.shared.service.StopService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.LatLng;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StopServiceImpl implements StopService {
    private final StopRepository stopRepository;
    private final StopMapper stopMapper;
	private final MapsServiceImpl mapsService;
	private final StudentRepository studentRepository;
	
	@Autowired
	public StopServiceImpl(@Nonnull final StopRepository stopRepository,
						   @Nonnull final StopMapper stopMapper,
						   @Nonnull final MapsServiceImpl mapsService,
						   @Nonnull final StudentRepository studentRepository) {
		this.stopRepository = stopRepository;
		this.stopMapper = stopMapper;
		this.mapsService = mapsService;
		this.studentRepository = studentRepository;
	}
	
	public StopDTO getStop(Integer id) {
		Optional<Stop> stop = stopRepository.findById(id);
        
        return stop.map(stopMapper::entityToDto).orElse(null);
	}

	public List<StopDTO> getRouteStops(Integer routeId) {
		List<Stop> stops = stopRepository.findByRouteIdOrderByRouteStopNumberAsc(routeId);

		return stops.stream().map(stopMapper::entityToDto).collect(Collectors.toList());
	}
	
	public List<StopDTO> getRouteStopsWithStudents(Integer routeId) {
		List<Stop> stops = stopRepository.findByRouteIdOrderByRouteStopNumberAsc(routeId);
		List<StopDTO> stopDTOs = new ArrayList<>();
		
		for (Stop stop : stops) {
			if (studentRepository.countByStopIdAndRiding(stop.getId(), true) > 0) {
				stopDTOs.add(stopMapper.entityToDto(stop));
			}
		}

		return stopDTOs;
	}
	
	public StopDTO getRouteStopByNumber(Integer routeId, Integer stopNumber) {
		Optional<Stop> stop = stopRepository.findByRouteIdAndRouteStopNumber(routeId, stopNumber);
        
        return stop.map(stopMapper::entityToDto).orElse(null);
        
    }

	public List<StopDTO> getUnassignedStops() {
		List<Stop> stops = stopRepository.findByRouteId(null);

		return stops.stream().map(stopMapper::entityToDto).collect(Collectors.toList());
	}

	public void addStop(StopCreationDTO stopCreationDTO, Integer routeId) {
		Stop newStop = stopMapper.creationDtoToEntity(stopCreationDTO);
		
		newStop.setStatus("Incomplete");
		newStop.setRouteId(routeId);

		stopRepository.save(newStop);
		
		stopMapper.entityToDto(newStop);
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
		Optional<Stop> stop = stopRepository.findById(stopId);
		
		if (stop.isPresent()) {
			Stop s = stop.get();
			s.setStatus("Completed");
			stopRepository.save(s);
		}
	}
	
	public void resetStopsForRoute(Integer routeId) {
		List<Stop> stops = stopRepository.findByRouteId(routeId);
		
		for (Stop s : stops) {
			s.setStatus("Incomplete");
			stopRepository.save(s);
		}
	}
}
