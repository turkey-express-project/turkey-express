package com.currency.turkey_express.domain.coupon.controller;

import com.currency.turkey_express.domain.coupon.dto.CouponList.CouponListRequestDto;
import com.currency.turkey_express.domain.coupon.dto.CouponList.CouponListResponseDto;
import com.currency.turkey_express.domain.coupon.service.CouponListService;
import com.currency.turkey_express.global.annotation.LoginRequired;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("users/{userId}/couponList")
public class CouponListController {

	private final CouponListService couponListService;

	/**
	 * TODO 민감한 데이터 로그 지우기
	 * 쿠폰 수령(유저별) API 쿠폰 리스트 테이블에 데이터 저장
	 */
	@LoginRequired
	@PostMapping
	public ResponseEntity<CouponListResponseDto> receivedCoupon(@PathVariable Long userId,
		@RequestBody CouponListRequestDto couponListRequestDto,
		HttpServletRequest httpServletRequest) throws IOException {

		//인터셉터에서 로그인된 사용자 ID 가져오기
		Long loginUserId = (Long) httpServletRequest.getAttribute("loginUserId");

		//콘솔 로그 확인
		log.info("userId - PathVariable: {}", userId);
		log.info("loginUserId - HttpServletRequest: {}", loginUserId);
		log.info("couponId: {}", couponListRequestDto.getCouponId());

		CouponListResponseDto couponListResponseDto = couponListService.receivedCoupon(
			userId,
			couponListRequestDto.getCouponId(),
			loginUserId
		);

		return new ResponseEntity<>(couponListResponseDto, HttpStatus.CREATED);
	}

	/**
	 * 쿠폰 목록(유저별) API
	 * 쿠폰 리스트 테이블 목록 조회
	 */

}
