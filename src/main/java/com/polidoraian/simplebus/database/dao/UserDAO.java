package com.polidoraian.simplebus.database.dao;

import com.polidoraian.simplebus.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, Integer>{
	public User findByEmail(String email);
}
