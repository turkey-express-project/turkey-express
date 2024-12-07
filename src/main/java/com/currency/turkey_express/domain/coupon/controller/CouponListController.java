package com.currency.turkey_express.domain.coupon.controller;

import com.currency.turkey_express.domain.coupon.dto.CouponList.CouponListRequestDto;
import com.currency.turkey_express.domain.coupon.dto.CouponList.CouponListResponseDto;
import com.currency.turkey_express.domain.coupon.service.CouponListService;
import com.currency.turkey_express.global.annotation.UserRequired;
import com.currency.turkey_express.global.base.enums.user.UserType;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	 * 유저별 쿠폰 수령 API
	 */
	@UserRequired(vaild = UserType.CUSTOMER)
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
	 * 유저별 쿠폰 전체 목록 조회 API
	 */
	@UserRequired(vaild = UserType.CUSTOMER)
	@GetMapping
	public ResponseEntity<List<CouponListResponseDto>> findAllCouponList(@PathVariable Long userId,
		HttpServletRequest httpServletRequest) throws IOException {

		//인터셉터에서 로그인된 사용자 ID 가져오기
		Long loginUserId = (Long) httpServletRequest.getAttribute("loginUserId");

		//콘솔 로그 확인
		log.info("userId - PathVariable: {}", userId);
		log.info("loginUserId - HttpServletRequest: {}", loginUserId);

		List<CouponListResponseDto> couponListResponseDto =
			couponListService.findAllCouponList(
				userId,
				loginUserId
			);

		return new ResponseEntity<>(couponListResponseDto, HttpStatus.OK);
	}

	/**
	 * 쿠폰 만료날짜가 되면 쿠폰 상태 변경 API
	 * - 매일 자정마다 업데이트 실행
	 */

}
