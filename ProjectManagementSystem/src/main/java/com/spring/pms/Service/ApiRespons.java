package com.spring.pms.Service;

import com.spring.pms.Entity.Project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRespons<T> {
	private String message;
    private T data;

   
}
