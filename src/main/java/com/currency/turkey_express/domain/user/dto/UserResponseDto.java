package com.currency.turkey_express.domain.user.dto;

import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.base.enums.user.UserStatus;
import com.currency.turkey_express.global.base.enums.user.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
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

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdAt; //유저 생성일

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime modifiedAt;//유저 정보 수정일

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime leavedAt; //탈퇴일

	private BigDecimal totalPoint; //총합 포인트

	public UserResponseDto(Long id, String email, String userNickname, String password,
		UserType userType, UserStatus userStatus, LocalDateTime createdAt, LocalDateTime modifiedAt,
		LocalDateTime leavedAt, BigDecimal totalPoint) {

		this.id = id;
		this.email = email;
		this.userNickname = userNickname;
		this.password = password;
		this.userType = userType;
		this.userStatus = userStatus;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.leavedAt = leavedAt;
		this.totalPoint = totalPoint;
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
			user.getCreatedAt(),
			user.getModifiedAt(),
			user.getLeavedAt(),
			user.getTotalPoint()
		);
	}

	//회원 탈퇴
	public UserResponseDto(User user) {
		this.id = user.getId();
		this.userNickname = user.getUserNickname();
		this.userStatus = user.getUserStatus();
	}


}
