package com.currency.turkey_express.domain.order.controller;

import com.currency.turkey_express.domain.cart.dto.CartCookieDto;
import com.currency.turkey_express.domain.coupon.dto.CouponResponseDto;
import com.currency.turkey_express.domain.order.dto.OrderCreateDto;
import com.currency.turkey_express.domain.order.dto.OrderRequestDto;
import com.currency.turkey_express.domain.order.service.OrderService;
import com.currency.turkey_express.domain.user.service.UserService;
import com.currency.turkey_express.global.annotation.LoginRequired;
import com.currency.turkey_express.global.base.dto.MessageDto;
import com.currency.turkey_express.global.constant.Const;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final ObjectMapper objectMapper;

	private final OrderService orderService;

	private final UserService userService;

	/**
	 * 장바구니 쿠키를 받아와서 주문을 생성하는 API
	 *
	 * @param userId           세션에 있는 유저 아이디
	 * @param encodedCartValue 쿠키에 있는 인코딩된 장바구니 데이터
	 * @param request          요청 객체
	 * @param orderRequestDto  주문시 요청 데이터 DTO
	 */
	@LoginRequired
	@PostMapping("")
	public void createOrder(
		@SessionAttribute(name = Const.LOGIN_USER) Long userId,
		@CookieValue(value = "CART") String encodedCartValue,
		HttpServletRequest request,
		@RequestBody OrderRequestDto orderRequestDto
	) {
		//쿠키에 있는 장바구니 데이터를 Jackson으로 객체로 파싱하기 가져오기

		CartCookieDto cartData = getCartCookieDto(
			encodedCartValue);

		// 주문 금액 총합 객체 초기화, 장바구니에 있는 총합으로 초기화
		BigDecimal totalPrice = cartData.getTotalPrice();

		//couponResponseDto 객체 초기화, 쿠폰 id 미제공 시 null로 초기화
		//coupon Entity를 DTO로 변환해서 가져온다.

		CouponResponseDto couponResponseDto = null;

		if (orderRequestDto.getCouponId() != null) {
			couponResponseDto = orderService.getCoupon(orderRequestDto.getCouponId());
		}

		//couponDiscountValue 객체 초기화, 쿠폰 Dto가 없다면 0으로 초기화
		BigDecimal couponDiscountValue = new BigDecimal(0);

		if (couponResponseDto != null) {
			couponDiscountValue = totalPrice.divide(
				BigDecimal.valueOf(Double.valueOf(couponResponseDto.getDiscountValue()) / 100D));
		}

		// couponDiscountValue 의 할인 금액 최대치 제한하기
		if (couponResponseDto != null
			&& couponDiscountValue.compareTo(couponResponseDto.getMaxDiscount()) > 0) {
			couponDiscountValue = couponResponseDto.getMaxDiscount();
		}

		// 쿠폰, 포인트 합이 총액을 넘을 수 없다.
		if (totalPrice.compareTo(
			couponDiscountValue.add(orderRequestDto.getPointPrice())) < 0) {
			throw new RuntimeException("가격 총합보다 할인 가격이 더 클 수 없습니다.");
		}

		// 장바구니 저장된 메뉴들을 주문 데이터 리스트로 변환, OrderCreateDto 객체 생성
		OrderCreateDto orderListData = new OrderCreateDto(
			userId,
			couponDiscountValue,
			cartData,
			orderRequestDto
		);

		// 주문 서비스 호출
		orderService.createOrder(orderListData);

		// 주문 완료 후 실제 포인트 차감, 포인트가 0보다 클 때
		if (orderRequestDto.getPointPrice().compareTo(BigDecimal.ZERO) > 0) {
			orderService.subtrackPoint(
				userId,
				orderRequestDto.getPointPrice()
			);
		}

		// 주문 완료 후 쿠폰 만료, 쿠폰 요청이 들어 왔을 때만
		if (orderRequestDto.getCouponId() != null) {
			orderService.expireCoupon(
				userId,
				orderRequestDto.getCouponId()
			);
		}

	}


	@LoginRequired
	@PatchMapping("/{orderId}")
	public ResponseEntity<MessageDto> processOrder(
		@SessionAttribute(name = Const.LOGIN_USER) Long userId,
		@PathVariable Long orderId
	) {
		//주문 접근해서 다음 상태로 변경
		orderService.processNext(orderId, userId);

		return new ResponseEntity<>(new MessageDto("다음 주문상태로 넘어갑니다"), HttpStatus.OK);
	}

	private CartCookieDto getCartCookieDto(String encodedCartValue) {
		if (encodedCartValue == null) {
			throw new RuntimeException("장바구니 정보가 없습니다.");
		}

		String decodedCartValue = URLDecoder.decode(encodedCartValue,
			StandardCharsets.UTF_8);

		CartCookieDto cartData = null;

		try {
			cartData = objectMapper.readValue(decodedCartValue, CartCookieDto.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		if (cartData.getMenuList() == null || cartData.getMenuList().isEmpty()) {
			throw new RuntimeException("장바구니에 메뉴를 담아주세요");
		}
		return cartData;
	}
}
