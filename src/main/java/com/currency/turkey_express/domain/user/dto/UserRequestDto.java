package com.currency.turkey_express.domain.user.dto;

import com.currency.turkey_express.global.base.enums.user.UserStatus;
import com.currency.turkey_express.global.base.enums.user.UserType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserRequestDto {

	private final Long id; //사용자 id
	private final String email; //이메일
	private final String userNickname; //닉네임
	private final String password; //비밀번호
	private final UserType userType; //유저 타입
	private final UserStatus userStatus; //유저 상태
	private final LocalDateTime leavedAt; //탈퇴일
	private final Integer totalPoint; //총합 포인트
	private final LocalDateTime createdAt; //유저 생성일
	private final LocalDateTime modifiedAt;//유저 정보 수정일

	public UserRequestDto(Long id, String email, String userNickname, String password,
		UserType userType, UserStatus userStatus, LocalDateTime leavedAt, Integer totalPoint,
		LocalDateTime createdAt, LocalDateTime modifiedAt) {
		this.id = id;
		this.email = email;
		this.userNickname = userNickname;
		this.password = password;
		this.userType = userType;
		this.userStatus = userStatus;
		this.leavedAt = leavedAt;
		this.totalPoint = totalPoint;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}
}
