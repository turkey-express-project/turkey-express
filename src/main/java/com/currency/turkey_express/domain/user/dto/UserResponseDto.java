package com.currency.turkey_express.domain.user.dto;

import com.currency.turkey_express.global.base.enums.user.UserStatus;
import com.currency.turkey_express.global.base.enums.user.UserType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserResponseDto {

	private Long id; //사용자 id
	private String email; //이메일
	private String userNickname; //닉네임
	private String password; //비밀번호
	private UserType userType; //유저 타입
	private UserStatus userStatus; //유저 상태
	private LocalDateTime leavedAt; //탈퇴일
	private Integer totalPoint; //총합 포인트
	private LocalDateTime createdAt; //유저 생성일
	private LocalDateTime modifiedAt;//유저 정보 수정일
}
