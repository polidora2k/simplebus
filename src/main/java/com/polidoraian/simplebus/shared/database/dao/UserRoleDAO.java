package com.polidoraian.simplebus.shared.database.dao;

import java.util.List;

import com.polidoraian.simplebus.shared.database.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleDAO extends JpaRepository<UserRole, Integer> {
	List<UserRole> findByUserId(Integer userId);
}
