package com.polidoraian.simplebus.shared.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.polidoraian.simplebus.shared.service.MapsService;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class MapsServiceImpl implements MapsService {
	//https://github.com/googlemaps/google-maps-services-java?utm_source=developers.google.com&utm_medium=referral
	private final GeoApiContext context;
	
	public MapsServiceImpl(@Nonnull final GeoApiContext context) {
		this.context = context;
	}
	
	public LatLng geocodeAddress(String address) {
		log.debug(context.toString());
		GeocodingResult[] results;
		try {
			results = GeocodingApi.geocode(context, address).await();
			for (GeocodingResult gr : results) {
				log.debug(gr.toString());
			}
		} catch (ApiException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			results = new GeocodingResult[1];
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			results = new GeocodingResult[1];
		} catch (IOException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			results = new GeocodingResult[1];
		} catch (Exception e) {
			log.debug(e.getMessage());
			results = new GeocodingResult[1];
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		log.debug("gson: " + gson.toJson(results[0].geometry));		
		
		return results[0].geometry.location;
	}
}
