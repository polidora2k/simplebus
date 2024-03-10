package com.polidoraian.simplebus.dto;

import jakarta.validation.constraints.NotEmpty;

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
public class StudentCreationDTO {
	@NotEmpty(message = "First name is required")
	private String firstName;
	
	@NotEmpty(message = "Last name is required")
	private String lastName;
	
	@NotEmpty(message = "Street Address is required")
	private String streetAddress;
	
	@NotEmpty(message = "City is required")
	private String city;
	
	@NotEmpty(message = "State is required")
	private String state;
	
	@NotEmpty(message = "Zipcode is required")
	private String zipcode;
}
