package com.currency.turkey_express.domain.coupon.controller;


import com.currency.turkey_express.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/coupons")
public class CouponController {

	private final CouponService couponService;

	/**
	 * 쿠폰 등록 API 쿠폰 테이블에 쿠폰 데이터 저장
	 */

	/**
	 * 쿠폰 조회 API 쿠폰 테이블에 저장된 쿠폰 데이터 조회
	 */


}
