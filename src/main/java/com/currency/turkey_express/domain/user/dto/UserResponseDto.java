package com.currency.turkey_express.domain.user.dto;

import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.base.enums.user.UserStatus;
import com.currency.turkey_express.global.base.enums.user.UserType;
import java.math.BigDecimal;
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
	private BigDecimal totalPoint; //총합 포인트
	private LocalDateTime createdAt; //유저 생성일
	private LocalDateTime modifiedAt;//유저 정보 수정일

	public UserResponseDto(Long id, String email, String userNickname, String password,
		UserType userType, UserStatus userStatus, LocalDateTime leavedAt, BigDecimal totalPoint,
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

	public UserResponseDto(Long id, String email, String userNickname, String password,
		UserType userType, UserStatus userStatus, BigDecimal totalPoint, LocalDateTime createdAt,
		LocalDateTime modifiedAt) {
		this.id = id;
		this.email = email;
		this.userNickname = userNickname;
		this.password = password;
		this.userType = userType;
		this.userStatus = userStatus;
		this.totalPoint = totalPoint;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	//회원가입
	public static UserResponseDto toDto(User user) {
		return new UserResponseDto(
			user.getId(),
			user.getEmail(),
			user.getUserNickname(),
			user.getPassword(),
			user.getUserType(),
			user.getUserStatus(),
			user.getTotalPoint(),
			user.getCreatedAt(),
			user.getModifiedAt()
		);

	}
}
