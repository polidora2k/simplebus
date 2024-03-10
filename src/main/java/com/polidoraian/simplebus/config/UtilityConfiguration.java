package com.polidoraian.simplebus.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilityConfiguration {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
