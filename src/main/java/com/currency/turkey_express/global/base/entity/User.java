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
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;

@Table(name = "user")
@Getter
@Entity
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "email", unique = true)
	private String email; //이메일

	@NotBlank
	@Column(name = "user_nickname", unique = true)
	private String userNickname; //닉네임

	@NotBlank
	@Column(name = "password", unique = true)
	private String password; //비밀번호

	@Enumerated(EnumType.STRING)
	@Column(name = "user_type", unique = true)
	private UserType userType; //유저 타입

	@Enumerated(EnumType.STRING)
	@Column(name = "user_status", unique = true)
	private UserStatus userStatus; //유저 상태

	@NotBlank
	@Column(name = "leaved_at", unique = true)
	private LocalDateTime leavedAt; //탈퇴일

	@NotBlank
	@Column(name = "total_point", unique = true)
	private Integer totalPoint; //총합 포인트


}
