package com.spring.pms.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {
	@Schema(example = "87aff2db-0d3e-48e1-99c2-2bee5ff884f5")
	private String token;

}
