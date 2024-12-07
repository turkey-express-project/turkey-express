package com.currency.turkey_express.domain.coupon.controller;


import com.currency.turkey_express.domain.coupon.dto.CouponAllResponseDto;
import com.currency.turkey_express.domain.coupon.dto.CouponRequestDto;
import com.currency.turkey_express.domain.coupon.service.CouponService;
import com.currency.turkey_express.global.annotation.UserRequired;
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
	 * 원래는 관리자만 쿠폰을 등록할 수 있다 쿠폰 등록 API 쿠폰 테이블에 쿠폰 데이터 저장
	 */
	@UserRequired
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
	 * 쿠폰 조회 API 쿠폰 테이블에 저장된 쿠폰 데이터 조회
	 */


}
