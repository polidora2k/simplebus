package com.polidoraian.simplebus.shared.service;

import com.google.maps.model.LatLng;

public interface MapsService {
    LatLng geocodeAddress(String address);
}
