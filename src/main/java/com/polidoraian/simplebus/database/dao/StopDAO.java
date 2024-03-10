package com.polidoraian.simplebus.database.dao;

import java.util.List;
import java.util.Optional;

import com.polidoraian.simplebus.database.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopDAO extends JpaRepository<Stop, Integer> {
	public Optional<Stop> findById(Integer id);
	
	public List<Stop> findByRouteIdOrderByRouteStopNumberAsc(Integer routeId);
	
	public List<Stop> findByRouteId(Integer routeId);
	
	public Optional<Stop> findByRouteIdAndRouteStopNumber(Integer routeId, Integer routeStopNumber);
}
