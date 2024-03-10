package com.polidoraian.simplebus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import com.polidoraian.simplebus.validation.EmailUnique;
import com.polidoraian.simplebus.validation.PasswordsMatch;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@PasswordsMatch(message = "Passwords must match.")
public class UserCreationDTO {
	private String role;
	
	@NotEmpty(message = "First name is required")
	private String firstName;
	
	@NotEmpty(message = "Last name is required")
	private String lastName;
	
	@Email
	@NotEmpty(message = "Email is required")
	@EmailUnique(message = "An account already exists with this email")
	@Length(max = 200, message = "Email must be less than 200 characters")
	private String email;
	
	@NotEmpty(message = "Password is required")
	//@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$&%!])[a-zA-Z\\d@#$&%!]{8,16}", message = "Password must be atleast 8 characters with one uppercase letter, one lowercase letter, a number, and one of the following special characters: @, #, $, &, %, !.")
	private String password;
	
	private String confirmPassword;
}
