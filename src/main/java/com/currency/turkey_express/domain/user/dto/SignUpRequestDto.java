package com.currency.turkey_express.domain.user.dto;

import com.currency.turkey_express.global.base.enums.user.UserType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

	@NotBlank
	private String email; //이메일

	@NotBlank
	private String userNickname; //닉네임

	@NotBlank
	private String password; //비밀번호

	@NotBlank
	private UserType userType; //유저 타입
}
