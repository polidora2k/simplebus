package com.polidoraian.simplebus.shared.repository;

import com.polidoraian.simplebus.shared.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
}
