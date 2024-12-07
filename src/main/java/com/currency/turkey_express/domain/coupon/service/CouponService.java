package com.currency.turkey_express.domain.coupon.service;

import com.currency.turkey_express.domain.coupon.dto.CouponAllResponseDto;
import com.currency.turkey_express.domain.coupon.repository.CouponRepository;
import com.currency.turkey_express.global.base.entity.Coupon;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	/**
	 * 쿠폰 등록 API
	 * - 관리자 전용
	 */
	@Transactional
	public CouponAllResponseDto createCoupon(String couponName, Integer discountValue,
		BigDecimal maxDiscount, LocalDateTime endDate) {
		Coupon coupon = new Coupon(couponName, discountValue, maxDiscount, endDate);

		//쿠폰 저장
		Coupon saveCoupon = couponRepository.save(coupon);

		return CouponAllResponseDto.toDto(saveCoupon);
	}

	/**
	 * 생성된 쿠폰 전체 조회
	 * - 관리자 전용
	 */
	public List<CouponAllResponseDto> findAllCoupons() {

		//쿠폰 전체 조회
		List<Coupon> coupons = couponRepository.findAll();

		List<CouponAllResponseDto> couponAllResponseDtoList = new ArrayList<>();

		for (Coupon couponList : coupons) {
			CouponAllResponseDto couponAllResponseDto = CouponAllResponseDto.toDto(couponList);
			couponAllResponseDtoList.add(couponAllResponseDto);
		}
		return couponAllResponseDtoList;
	}

}
