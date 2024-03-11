package com.polidoraian.simplebus.shared.database.dao;

import java.util.Optional;

import com.polidoraian.simplebus.shared.database.entity.Route;
import jakarta.annotation.Nonnull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteDAO extends JpaRepository<Route, Integer> {
	@NotNull
	Optional<Route> findById(@Nonnull Integer id);
}
