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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;

@Table(name = "user")
@Getter
@Entity
//@DynamicInsert
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //사용자 id

	@Column(name = "email", unique = true)
	private String email; //이메일

	@Column(nullable = false)
	private String userNickname; //닉네임

	@Column(nullable = false)
	private String password; //비밀번호

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserType userType; //유저 타입

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserStatus userStatus; //유저 상태

	private LocalDateTime leavedAt; //탈퇴일

	@Column(nullable = false)
	private BigDecimal totalPoint = new BigDecimal(0); //총합 포인트

	public User(String email, String userNickname, String password, UserType userType,
		UserStatus userStatus) {
		this.email = email;
		this.userNickname = userNickname;
		this.password = password;
		this.userType = userType;
		this.userStatus = userStatus;
	}

	public User() {
	}

}
