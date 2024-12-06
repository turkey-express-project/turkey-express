package com.currency.turkey_express.domain.coupon.dto.CouponList;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CouponListRequestDto {
	
	@NotBlank
	private Long couponId; //쿠폰 id(외래키)

}
