package com.currency.turkey_express.domain.coupon.dto;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class CouponAllResponseDto {

	private Long id; //쿠폰 id
	private String couponName; //쿠폰 이름
	private Integer discountValue; //쿠폰 할인률
	private BigDecimal maxDiscount; //쿠폰 최대 할인금액

}
