package com.currency.turkey_express.domain.coupon.dto;

import com.currency.turkey_express.global.base.entity.Coupon;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CouponAllResponseDto {

	private Long id; //쿠폰 id
	
	private String couponName; //쿠폰 이름

	private Integer discountValue; //쿠폰 할인률

	private BigDecimal maxDiscount; //쿠폰 최대 할인금액

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdAt; //쿠폰 생성일

	private LocalDateTime endDate;//쿠폰 만료일자

	public CouponAllResponseDto(Long id, String couponName, Integer discountValue,
		BigDecimal maxDiscount, LocalDateTime createdAt, LocalDateTime endDate) {
		this.id = id;
		this.couponName = couponName;
		this.discountValue = discountValue;
		this.maxDiscount = maxDiscount;
		this.createdAt = createdAt;
		this.endDate = endDate;
	}


	public static CouponAllResponseDto toDto(Coupon coupon) {
		return new CouponAllResponseDto(
			coupon.getId(),
			coupon.getCouponName(),
			coupon.getDiscountValue(),
			coupon.getMaxDiscount(),
			coupon.getCreatedAt(),
			coupon.getEndDate()
		);
	}
}
