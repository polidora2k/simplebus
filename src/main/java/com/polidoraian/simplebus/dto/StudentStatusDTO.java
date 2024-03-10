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
public class StudentStatusDTO {
	private StopDTO previousStop;
	private Boolean arrived;
	private Integer percent;
	private Boolean routeInProgress;
}
