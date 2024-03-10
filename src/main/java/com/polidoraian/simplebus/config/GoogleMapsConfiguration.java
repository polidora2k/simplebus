package com.polidoraian.simplebus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.google.maps.GeoApiContext;

@Configuration
public class GoogleMapsConfiguration {
	
	private String apiKey = "AIzaSyB3p3rsbl0XY4BbtDdSpik9s1ms7_b49iw";

	@Bean
	@Scope("singleton")
	public GeoApiContext context() {
		return new GeoApiContext.Builder().apiKey(apiKey).build();
	}
}
