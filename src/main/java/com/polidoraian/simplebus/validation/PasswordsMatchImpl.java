package com.polidoraian.simplebus.validation;

import com.polidoraian.simplebus.dto.UserCreationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchImpl implements ConstraintValidator<PasswordsMatch, Object> {
	@Override
	public void initialize(PasswordsMatch constraintAnnotation) {
		
	}
	
	@Override
	public boolean isValid(Object user, ConstraintValidatorContext context) {
		UserCreationDTO userDTO = (UserCreationDTO) user;
		
		return userDTO.getPassword().equals(userDTO.getConfirmPassword());
	}
}
