package com.currency.turkey_express.domain.coupon.dto;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class CouponRequestDto {

	@NotBlank
	private final String couponName; //쿠폰 이름

	@NotBlank
	private final Integer discountValue; //쿠폰 할인률

	@NotBlank
	private final BigDecimal maxDiscount; //쿠폰 최대 할인금액

	public CouponRequestDto(String couponName, Integer discountValue, BigDecimal maxDiscount) {
		this.couponName = couponName;
		this.discountValue = discountValue;
		this.maxDiscount = maxDiscount;
	}
}
