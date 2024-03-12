package com.polidoraian.simplebus.shared.repository;

import java.util.List;

import com.polidoraian.simplebus.shared.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
	List<UserRole> findByUserId(Integer userId);
}
