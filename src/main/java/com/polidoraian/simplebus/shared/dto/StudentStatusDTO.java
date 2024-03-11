package com.polidoraian.simplebus.shared.dto;

import lombok.Data;

@Data
public class StudentStatusDTO {
	private StopDTO previousStop;
	private Boolean arrived;
	private Integer percent;
	private Boolean routeInProgress;
}
