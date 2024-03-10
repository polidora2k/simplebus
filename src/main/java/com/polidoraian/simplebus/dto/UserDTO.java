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
public class UserDTO {
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
}
