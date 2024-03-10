package com.polidoraian.simplebus.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
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
