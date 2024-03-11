package com.polidoraian.simplebus.shared.validation;

import com.polidoraian.simplebus.shared.dto.UserCreationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchImpl implements ConstraintValidator<PasswordsMatch, Object> {
    
    @Override
	public boolean isValid(Object user, ConstraintValidatorContext context) {
		UserCreationDTO userDTO = (UserCreationDTO) user;
		
		return userDTO.getPassword().equals(userDTO.getConfirmPassword());
	}
}
