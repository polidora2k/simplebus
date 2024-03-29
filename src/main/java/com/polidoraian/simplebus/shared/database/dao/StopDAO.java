package com.polidoraian.simplebus.shared.database.dao;

import java.util.List;
import java.util.Optional;

import com.polidoraian.simplebus.shared.database.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopDAO extends JpaRepository<Stop, Integer> {
	Optional<Stop> findById(Integer id);
	
	List<Stop> findByRouteIdOrderByRouteStopNumberAsc(Integer routeId);
	
	List<Stop> findByRouteId(Integer routeId);
	
	Optional<Stop> findByRouteIdAndRouteStopNumber(Integer routeId, Integer routeStopNumber);
}
