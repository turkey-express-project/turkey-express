package com.currency.turkey_express.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

	@NotBlank
	private String email; //이메일

	@NotBlank
	private String password; //비밀번호


}
