package com.polidoraian.simplebus.shared.repository;

import java.util.List;
import java.util.Optional;

import com.polidoraian.simplebus.shared.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends JpaRepository<Stop, Integer> {
	List<Stop> findByRouteIdOrderByRouteStopNumberAsc(Integer routeId);
	
	List<Stop> findByRouteId(Integer routeId);
	
	Optional<Stop> findByRouteIdAndRouteStopNumber(Integer routeId, Integer routeStopNumber);
}
