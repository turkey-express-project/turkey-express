package com.currency.turkey_express.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserDeleteRequestDto {


	@NotBlank(message = "비밀번호는 필수 입력값입니다.")
	private String password; //비밀번호

	public UserDeleteRequestDto(String password) {
		this.password = password;
	}
}
