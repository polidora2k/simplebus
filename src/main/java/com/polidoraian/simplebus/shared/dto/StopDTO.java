package com.polidoraian.simplebus.shared.dto;

import lombok.Data;

@Data
public class StopDTO {
	private Integer id;
	private String name;
	private String streetAddress;
	private String city;
	private String state;
	private String zipcode;
	private String status;
	private Integer routeId;
	private Integer routeStopNumber;
}
