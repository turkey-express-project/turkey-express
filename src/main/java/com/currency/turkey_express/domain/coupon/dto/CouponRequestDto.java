package com.currency.turkey_express.domain.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CouponRequestDto {

	@NotNull(message = "쿠폰 이름은 필수 입력값입니다.")
	private final String couponName; //쿠폰 이름

	@NotNull(message = "쿠폰 할인률은 필수 입력값입니다.")
	private final Integer discountValue; //쿠폰 할인률

	@NotNull(message = "쿠폰 최대 할인금액은 필수 입력값입니다.")
	private final BigDecimal maxDiscount; //쿠폰 최대 할인금액

	@NotNull(message = "쿠폰 만료일자는 필수 입력값입니다.")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime endDate;//쿠폰 만료일자

	public CouponRequestDto(String couponName, Integer discountValue, BigDecimal maxDiscount,
		LocalDateTime endDate) {
		this.couponName = couponName;
		this.discountValue = discountValue;
		this.maxDiscount = maxDiscount;
		this.endDate = endDate;
	}
}
