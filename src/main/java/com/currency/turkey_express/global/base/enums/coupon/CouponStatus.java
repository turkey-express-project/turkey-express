package com.currency.turkey_express.global.base.enums.coupon;

import lombok.Getter;

@Getter
public enum CouponStatus {
	OK(1, "유효"), EXPIRED(0, "만료");

	private final Integer statusNumber;
	private final String statusText;

	CouponStatus(Integer statusNumber, String statusText) {
		this.statusNumber = statusNumber;
		this.statusText = statusText;
	}
}
