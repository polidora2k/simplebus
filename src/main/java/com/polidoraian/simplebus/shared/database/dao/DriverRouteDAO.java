package com.polidoraian.simplebus.shared.database.dao;

import java.util.List;
import java.util.Optional;

import com.polidoraian.simplebus.shared.database.entity.DriverRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRouteDAO extends JpaRepository<DriverRoute, Integer>{
	List<DriverRoute> findByUserId(Integer userId);
	
	@Query(value = "select dr.id, dr.user_id, dr.route_id from driver_routes dr join routes r on r.id = dr.route_id where dr.user_id = :driverId and r.status != 'Completed'", nativeQuery = true)
	List<DriverRoute> findIncompleteRoutesByDriver(Integer driverId);
	
	Optional<DriverRoute> findByRouteId(Integer routeId);
}