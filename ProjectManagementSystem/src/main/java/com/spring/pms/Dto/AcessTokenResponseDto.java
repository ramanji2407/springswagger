package com.spring.pms.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcessTokenResponseDto {
	private String jwtToken;
	private String refreshToken;

}
