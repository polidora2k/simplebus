package com.polidoraian.simplebus.shared.dto;

import lombok.Data;

@Data
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
