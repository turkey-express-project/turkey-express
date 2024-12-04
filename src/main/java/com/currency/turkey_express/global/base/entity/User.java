package com.currency.turkey_express.global.base.entity;

import com.currency.turkey_express.global.base.enums.user.UserStatus;
import com.currency.turkey_express.global.base.enums.user.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;

@Table(name = "user")
@Getter
@Entity
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //사용자 id

	@Column(name = "email", unique = true)
	private String email; //이메일

	@Column(name = "user_nickname")
	private String userNickname; //닉네임

	@Column(name = "password")
	private String password; //비밀번호

	@Enumerated(EnumType.STRING)
	@Column(name = "user_type")
	private UserType userType; //유저 타입

	@Enumerated(EnumType.STRING)
	@Column(name = "user_status")
	private UserStatus userStatus; //유저 상태

	@Column(name = "leaved_at")
	private LocalDateTime leavedAt; //탈퇴일

	@Column(name = "total_point")
	private Integer totalPoint; //총합 포인트


}
