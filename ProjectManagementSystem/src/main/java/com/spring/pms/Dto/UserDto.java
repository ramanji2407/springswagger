package com.spring.pms.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	 private int id;
	 private String name;
	 private String email;
	 private String role;
	 private String department;

}
