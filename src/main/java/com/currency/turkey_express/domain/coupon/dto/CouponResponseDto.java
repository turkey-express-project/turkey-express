package com.currency.turkey_express.domain.coupon.dto;

import com.currency.turkey_express.global.base.entity.Coupon;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CouponResponseDto {

	private Long id; //쿠폰 id

	private Integer discountValue; //쿠폰 할인률

	private BigDecimal maxDiscount; //쿠폰 최대 할인금액
	

	public CouponResponseDto(Coupon coupon) {
		this.id = coupon.getId();
		this.discountValue = coupon.getDiscountValue();
		this.maxDiscount = coupon.getMaxDiscount();
	}
}
