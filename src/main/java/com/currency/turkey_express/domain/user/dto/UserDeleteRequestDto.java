package com.currency.turkey_express.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserDeleteRequestDto {

	@NotBlank
	private String password; //비밀번호

	public UserDeleteRequestDto(String password) {
		this.password = password;
	}
}
