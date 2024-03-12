package com.polidoraian.simplebus.shared.service.impl;

import com.polidoraian.simplebus.shared.repository.UserRepository;
import com.polidoraian.simplebus.shared.repository.UserRoleRepository;
import com.polidoraian.simplebus.shared.entity.User;
import com.polidoraian.simplebus.shared.entity.UserRole;
import com.polidoraian.simplebus.shared.dto.UserCreationDTO;
import com.polidoraian.simplebus.shared.dto.mapper.UserMapper;
import com.polidoraian.simplebus.shared.service.UserService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final UserRoleRepository userRoleRepository;
	@Qualifier("passwordEncoder")
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(@Nonnull final UserRepository userRepository,
						   @Nonnull final UserMapper userMapper,
						   @Nonnull final UserRoleRepository userRoleRepository,
						   @Nonnull final PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public void signup(UserCreationDTO userCreationDTO) {
		User newUser = userMapper.creationDtoToEntity(userCreationDTO);
		UserRole userRole = new UserRole();
		
		String encodedPassword = passwordEncoder.encode(userCreationDTO.getPassword());
		
		newUser.setPassword(encodedPassword);
		
		log.debug(newUser.toString());
		userRepository.save(newUser);
		
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
		
		userRoleRepository.save(userRole);
		
		userMapper.entityToDto(newUser);
	}
}
