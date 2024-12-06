package com.currency.turkey_express.domain.coupon.service;

import com.currency.turkey_express.domain.coupon.dto.CouponAllResponseDto;
import com.currency.turkey_express.domain.coupon.repository.CouponRepository;
import com.currency.turkey_express.global.base.entity.Coupon;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	/**
	 * 쿠폰 등록 API 쿠폰 테이블에 쿠폰 데이터 저장
	 */
	@Transactional
	public CouponAllResponseDto createCoupon(String couponName, Integer discountValue,
		BigDecimal maxDiscount) {
		Coupon coupon = new Coupon(couponName, discountValue, maxDiscount);

		//쿠폰 저장
		Coupon saveCoupon = couponRepository.save(coupon);

		return CouponAllResponseDto.toDto(saveCoupon);
	}

}
