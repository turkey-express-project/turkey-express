package com.currency.turkey_express.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

	@NotBlank(message = "이메일은 필수 입력값입니다.")
	private final String email; //이메일

	@NotBlank(message = "비밀번호는 필수 입력값입니다.")
	private String password; //비밀번호


	public LoginRequestDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
