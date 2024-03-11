package com.polidoraian.simplebus.shared.database.dao;

import com.polidoraian.simplebus.shared.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, Integer>{
	User findByEmail(String email);
}
