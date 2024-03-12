package com.polidoraian.simplebus.shared.dto.mapper;

import com.polidoraian.simplebus.shared.entity.User;
import com.polidoraian.simplebus.shared.dto.UserCreationDTO;
import com.polidoraian.simplebus.shared.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User dtoToEntity(UserDTO userDTO);
    
    @Mapping(target = "id", ignore = true)
    User creationDtoToEntity(UserCreationDTO userCreationDTO);
    
    UserDTO entityToDto(User user);
}
