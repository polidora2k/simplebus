package com.polidoraian.simplebus.service;

import com.polidoraian.simplebus.database.dao.UserDAO;
import com.polidoraian.simplebus.database.dao.UserRoleDAO;
import com.polidoraian.simplebus.database.entity.User;
import com.polidoraian.simplebus.database.entity.UserRole;
import com.polidoraian.simplebus.dto.UserCreationDTO;
import com.polidoraian.simplebus.dto.UserDTO;
import com.polidoraian.simplebus.dto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	public UserDTO signup(UserCreationDTO userCreationDTO) {
		User newUser = userMapper.toUser(userCreationDTO);
		UserRole userRole = new UserRole();
		
		String encodedPassword = passwordEncoder.encode(userCreationDTO.getPassword());
		
		newUser.setPassword(encodedPassword);
		
		log.debug(newUser.toString());
		userDAO.save(newUser);
		
		userRole.setUserId(newUser.getId());
		
		switch (userCreationDTO.getRole()) {
			case "Parent":
				userRole.setRoleName("PARENT");
				break;
			case "Driver":
				userRole.setRoleName("DRIVER");
				break;
			default:
				break;
		}
		
		userRoleDAO.save(userRole);
		
		return userMapper.toUserDTO(newUser);
	}
}
