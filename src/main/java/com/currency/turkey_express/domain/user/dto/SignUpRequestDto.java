package com.currency.turkey_express.domain.user.dto;

import com.currency.turkey_express.global.base.enums.user.UserType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

	@NotBlank
	private final String email; //이메일

	@NotBlank
	private final String userNickname; //닉네임

	@NotBlank
	private final String password; //비밀번호

	@NotBlank
	private final UserType userType; //유저 타입

	public SignUpRequestDto(String email, String userNickname, String password, UserType userType) {
		this.email = email;
		this.userNickname = userNickname;
		this.password = password;
		this.userType = userType;
	}
}
