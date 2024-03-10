package com.polidoraian.simplebus.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import com.polidoraian.simplebus.database.dao.UserDAO;
import com.polidoraian.simplebus.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailUniqueImpl implements ConstraintValidator<EmailUnique, String> {
	@Autowired
	private UserDAO userDao;

	@Override
	public void initialize(EmailUnique constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if ( StringUtils.isEmpty(value) ) {
			return true;
		}
		
		User user = userDao.findByEmail(value);
		
		return ( user == null );
	}
}
