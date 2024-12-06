package com.currency.turkey_express.domain.coupon.dto.CouponList;

import com.currency.turkey_express.global.base.entity.CouponList;
import com.currency.turkey_express.global.base.enums.coupon.CouponStatus;
import lombok.Getter;

@Getter
public class CouponListResponseDto {

	private Long id; //쿠폰 리스트 id

	//연관관계 - N:1
	private Long userId; //사용자 id(외래키)

	//연관관계 - N:1
	private Long couponId; //쿠폰 id(외래키)

	private CouponStatus status; //쿠폰 상태

	public CouponListResponseDto(Long id, Long userId, Long couponId, CouponStatus status) {
		this.id = id;
		this.userId = userId;
		this.couponId = couponId;
		this.status = status;
	}

	public static CouponListResponseDto toDto(CouponList couponList) {
		return new CouponListResponseDto(
			couponList.getId(),
			couponList.getUser().getId(),
			couponList.getCoupon().getId(),
			couponList.getStatus()
		);
	}
}
