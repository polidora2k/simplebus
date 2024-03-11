package com.polidoraian.simplebus.shared.dto.mapper;

import com.polidoraian.simplebus.shared.database.entity.User;
import com.polidoraian.simplebus.shared.dto.UserCreationDTO;
import com.polidoraian.simplebus.shared.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	@Autowired
	private ModelMapper mapper;

	public UserCreationDTO toUserCreationDTO(User user) {
		return mapper.map(user, UserCreationDTO.class);
	}

	public User toUser(UserCreationDTO userCreationDTO) {

		return mapper.map(userCreationDTO, User.class);
	}

	public User toUser(UserDTO userDTO) {

		return mapper.map(userDTO, User.class);
	}

	public UserDTO toUserDTO(User user) {
		return mapper.map(user, UserDTO.class);
	}

}
