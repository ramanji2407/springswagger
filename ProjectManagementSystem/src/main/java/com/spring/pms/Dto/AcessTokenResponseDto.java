package com.spring.pms.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcessTokenResponseDto {
	@Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYW0iLCJpYXQiOjE2OTQwNjAxNDMsImV4cCI6MTY5NDE0NjU0M30.TpVjJdgxHiFSPSVGTOCkvFXeE3kYuKttXd9hOJssGVo")
	private String jwtToken;
	@Schema(example = "87aff2db-0d3e-48e1-99c2-2bee5ff884f5")
	private String refreshToken;

}
