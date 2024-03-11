package com.polidoraian.simplebus.shared.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.google.maps.GeoApiContext;

@Configuration
public class GoogleMapsConfiguration {
	@Value("google.api-key")
	private String apiKey;

	@Bean
	@Scope("singleton")
	public GeoApiContext context() {
		return new GeoApiContext.Builder().apiKey(apiKey).build();
	}
}
