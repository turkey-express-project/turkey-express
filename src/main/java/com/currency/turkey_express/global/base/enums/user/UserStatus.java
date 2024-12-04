package com.currency.turkey_express.global.base.enums.user;

import lombok.Getter;

@Getter
public enum UserStatus {
	REGISTER(1, "정상"), DELETE(0, "탈퇴");

	private final Integer statusNumber;
	private final String statusText;

	UserStatus(Integer statusNumber, String statusText) {
		this.statusNumber = statusNumber;
		this.statusText = statusText;
	}
}
