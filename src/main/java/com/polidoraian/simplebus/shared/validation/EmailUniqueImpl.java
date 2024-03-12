package com.polidoraian.simplebus.shared.validation;


import com.polidoraian.simplebus.shared.repository.UserRepository;
import com.polidoraian.simplebus.shared.entity.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailUniqueImpl implements ConstraintValidator<EmailUnique, String> {
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if ( StringUtils.isEmpty(value) ) {
			return true;
		}
		
		User user = userRepository.findByEmail(value);
		
		return ( user == null );
	}
}
