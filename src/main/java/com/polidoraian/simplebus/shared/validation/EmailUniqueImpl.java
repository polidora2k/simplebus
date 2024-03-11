package com.polidoraian.simplebus.shared.validation;


import com.polidoraian.simplebus.shared.database.dao.UserDAO;
import com.polidoraian.simplebus.shared.database.entity.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailUniqueImpl implements ConstraintValidator<EmailUnique, String> {
	@Autowired
	private UserDAO userDao;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if ( StringUtils.isEmpty(value) ) {
			return true;
		}
		
		User user = userDao.findByEmail(value);
		
		return ( user == null );
	}
}
