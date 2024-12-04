package com.currency.turkey_express.global.base.enums.user;

import lombok.Getter;

@Getter
public enum UserType {
	COSTOMER(1, "고객"), OWNER(0, "사장");

	private final Integer statusNumber;
	private final String statusText;

	UserType(Integer statusNumber, String statusText) {
		this.statusNumber = statusNumber;
		this.statusText = statusText;
	}
}
