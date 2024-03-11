package com.polidoraian.simplebus.shared.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class StopCreationDTO {
	@NotEmpty(message = "First name is required")
	private String name;
	
	@NotEmpty(message = "Street Address is required")
	private String streetAddress;
	
	@NotEmpty(message = "City is required")
	private String city;
	
	@NotEmpty(message = "State is required")
	private String state;
	
	@NotEmpty(message = "Zipcode is required")
	private String zipcode;
}
