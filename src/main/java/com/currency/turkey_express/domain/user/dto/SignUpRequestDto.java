package com.currency.turkey_express.domain.user.dto;

import com.currency.turkey_express.global.base.enums.user.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

	@NotBlank(message = "이메일은 필수 입력값입니다.")
	@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
	private final String email; //이메일

	@NotBlank(message = "닉네임은 필수 입력값입니다.")
	private String userNickname; //닉네임

	@NotBlank(message = "비밀번호는 필수 입력값입니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 최소 8글자 이상이며, 영문, 숫자, 특수문자를 1개씩 포함해야합니다.")
	private String password; //비밀번호

	/**
	 * TODO ENUM 유효성 검사 : 커스텀 어노테이션 만들어야 한다
	 */
	@NotNull
	private UserType userType; //유저 타입

	public SignUpRequestDto(String email, String userNickname, String password, UserType userType) {
		this.email = email;
		this.userNickname = userNickname;
		this.password = password;
		this.userType = userType;
	}
}
