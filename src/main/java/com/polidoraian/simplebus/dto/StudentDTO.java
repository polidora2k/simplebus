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
public class StudentDTO {
	private Integer id;
	private String firstName;
	private String lastName;
	private String streetAddress;
	private String city;
	private String state;
	private String zipcode;
	private Integer parentId;
	private Boolean riding;
	private Integer stopId;
	private StudentStatusDTO status;
}
