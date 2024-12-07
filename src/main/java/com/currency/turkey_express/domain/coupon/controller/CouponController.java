package com.currency.turkey_express.domain.coupon.controller;


import com.currency.turkey_express.domain.coupon.dto.CouponAllResponseDto;
import com.currency.turkey_express.domain.coupon.dto.CouponRequestDto;
import com.currency.turkey_express.domain.coupon.service.CouponService;
import com.currency.turkey_express.global.annotation.UserRequired;
import com.currency.turkey_express.global.base.enums.user.UserType;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/coupons")
public class CouponController {

	private final CouponService couponService;

	/**
	 * 쿠폰 등록 API - 관리자 전용
	 */
	@UserRequired(vaild = UserType.ADMIN)
	@PostMapping
	public ResponseEntity<CouponAllResponseDto> createCoupon(@Valid
	@RequestBody CouponRequestDto couponRequestDto)
		throws IOException {

		//콘솔 로그 확인
		log.info("CouponName: {}", couponRequestDto.getCouponName());
		log.info("DiscountValue: {}", couponRequestDto.getDiscountValue());
		log.info("MaxDiscount: {}", couponRequestDto.getMaxDiscount());
		log.info("getEndDate: {}", couponRequestDto.getEndDate());

		CouponAllResponseDto couponAllResponseDto = couponService.createCoupon(
			couponRequestDto.getCouponName(),
			couponRequestDto.getDiscountValue(),
			couponRequestDto.getMaxDiscount(),
			couponRequestDto.getEndDate()
		);

		return new ResponseEntity<>(couponAllResponseDto, HttpStatus.CREATED);
	}

	/**
	 * 생성된 쿠폰 전체 조회 API
	 */


}
