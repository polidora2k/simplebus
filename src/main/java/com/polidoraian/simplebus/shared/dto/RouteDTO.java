package com.polidoraian.simplebus.shared.dto;

import lombok.Data;

@Data
public class RouteDTO {
	private Integer id;
	private String name;
	private String status;
	private Integer lastCompletedStopId;
}
