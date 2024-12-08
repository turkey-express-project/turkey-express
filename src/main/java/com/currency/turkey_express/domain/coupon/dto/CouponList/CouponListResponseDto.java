package com.currency.turkey_express.domain.coupon.dto.CouponList;

import com.currency.turkey_express.global.base.entity.CouponList;
import com.currency.turkey_express.global.base.enums.coupon.CouponStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CouponListResponseDto {

	private Long id; //쿠폰 리스트 id

	private Long userId; //사용자 id(외래키)

	private Long couponId; //쿠폰 id(외래키)

	private String couponName; //쿠폰 이름

	private Integer discountValue; //쿠폰 할인률

	private BigDecimal maxDiscount; //쿠폰 최대 할인금액

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdAt; //쿠폰 생성일
	
	private LocalDateTime endDate;//쿠폰 만료일자

	private CouponStatus status; //쿠폰 상태

	public CouponListResponseDto(Long id, Long userId, Long couponId, String couponName,
		Integer discountValue, BigDecimal maxDiscount, LocalDateTime createdAt,
		LocalDateTime endDate,
		CouponStatus status) {
		this.id = id;
		this.userId = userId;
		this.couponId = couponId;
		this.couponName = couponName;
		this.discountValue = discountValue;
		this.maxDiscount = maxDiscount;
		this.createdAt = createdAt;
		this.endDate = endDate;
		this.status = status;
	}

	public static CouponListResponseDto toDto(CouponList couponList) {
		return new CouponListResponseDto(
			couponList.getId(),
			couponList.getUser().getId(),
			couponList.getCoupon().getId(),
			couponList.getCoupon().getCouponName(),
			couponList.getCoupon().getDiscountValue(),
			couponList.getCoupon().getMaxDiscount(),
			couponList.getCoupon().getCreatedAt(),
			couponList.getCoupon().getEndDate(),
			couponList.getStatus()
		);
	}


}
