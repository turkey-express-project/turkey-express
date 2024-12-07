package com.currency.turkey_express.domain.order.controller;

import com.currency.turkey_express.domain.cart.dto.CartCookieDto;
import com.currency.turkey_express.domain.cart.exception.NoExistException;
import com.currency.turkey_express.domain.coupon.dto.CouponResponseDto;
import com.currency.turkey_express.domain.order.dto.CancleRequestDto;
import com.currency.turkey_express.domain.order.dto.OrderCreateDto;
import com.currency.turkey_express.domain.order.dto.OrderRequestDto;
import com.currency.turkey_express.domain.order.service.OrderService;
import com.currency.turkey_express.domain.user.service.UserService;
import com.currency.turkey_express.global.annotation.UserRequired;
import com.currency.turkey_express.global.base.dto.MessageDto;
import com.currency.turkey_express.global.base.enums.order.OrderStatus;
import com.currency.turkey_express.global.base.enums.user.UserType;
import com.currency.turkey_express.global.constant.Const;
import com.currency.turkey_express.global.exception.ExceptionResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

	private final OrderService orderService;
	private final UserService userService;

	/**
	 * 장바구니 쿠키를 받아와서 주문을 생성하는 API
	 *
	 * @param userId
	 * @param cartData
	 * @param orderRequestDto
	 * @return
	 */
	@UserRequired(vaild = UserType.CUSTOMER)
	@PostMapping("")
	public ResponseEntity<MessageDto> createOrder(
		@SessionAttribute(name = Const.LOGIN_USER) Long userId,
		@CookieValue(value = "CART", required = false) CartCookieDto cartData,
		@RequestBody OrderRequestDto orderRequestDto,
		HttpServletResponse response
	) {

		if (cartData == null) {
			throw new IllegalArgumentException("장바구니가 없습니다");
		}

		// 주문 금액 총합 객체 초기화, 장바구니에 있는 총합으로 초기화
		BigDecimal totalPrice = cartData.getTotalPrice();

		// 주문자에게 요청한 포인트가 있는 지 확인
		if (orderService.getCustomerPointTotal(userId).compareTo(
			orderRequestDto.getPointPrice()
		) < 0) {
			throw new IllegalArgumentException("사용 포인트가 보유한 포인트를 초과합니다");
		}

		//couponResponseDto 객체 초기화, 쿠폰 id 미제공 시 null로 초기화
		//coupon Entity를 DTO로 변환해서 가져온다.

		CouponResponseDto couponResponseDto = null;

		if (orderRequestDto.getCouponId() != null) {
			couponResponseDto = orderService.getCoupon(orderRequestDto.getCouponId());
		}

		// 쿠폰 할인 금액 계산
		BigDecimal couponDiscountValue = calcCouponDiscountValue(couponResponseDto, totalPrice);

		// 쿠폰, 포인트 합이 총액을 넘을 수 없다.
		if (totalPrice.compareTo(
			couponDiscountValue.add(orderRequestDto.getPointPrice())) < 0) {
			throw new IllegalArgumentException("가격 총합보다 할인 가격이 더 클 수 없습니다.");
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
			orderService.subtractPoint(
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

		// 쿠키 비우기
		Cookie cartContentCookie = new Cookie("CART", "");

		cartContentCookie.setMaxAge(0);
		cartContentCookie.setPath("/");

		response.addCookie(cartContentCookie);

		return new ResponseEntity<>(new MessageDto("주문 요청 완료"), HttpStatus.CREATED);
	}

	/**
	 * 쿠폰 할인 금액 계산 함수
	 *
	 * @param couponResponseDto
	 * @param totalPrice
	 * @return
	 */
	private BigDecimal calcCouponDiscountValue(CouponResponseDto couponResponseDto,
		BigDecimal totalPrice) {
		// couponDiscountValue 객체 초기화, 쿠폰 Dto가 없다면 0으로 초기화
		BigDecimal couponDiscountValue = new BigDecimal(0);

		if (couponResponseDto != null) {
			couponDiscountValue = totalPrice.multiply(
				BigDecimal.valueOf(couponResponseDto.getDiscountValue())
					.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
			);
		}

		// couponDiscountValue 의 할인 금액 최대치 제한하기
		if (couponResponseDto != null
			&& couponDiscountValue.compareTo(couponResponseDto.getMaxDiscount()) > 0) {
			couponDiscountValue = couponResponseDto.getMaxDiscount();
		}
		return couponDiscountValue;
	}

	/**
	 * 주문 진행 API
	 *
	 * @param userId  유저 아이디
	 * @param orderId 주문 아이디
	 * @return
	 */
	@UserRequired(vaild = UserType.OWNER)
	@PatchMapping("/{orderId}")
	public ResponseEntity<MessageDto> processOrder(
		@SessionAttribute(name = Const.LOGIN_USER) Long userId,
		@PathVariable Long orderId
	) {
		//주문 접근해서 다음 상태로 변경
		OrderStatus orderStatus = orderService.processNext(orderId, userId);

		return new ResponseEntity<>(new MessageDto("주문 상태 : " + orderStatus.toString()),
			HttpStatus.OK);
	}

	/**
	 * 주문 최소 API
	 *
	 * @param userId           유저 아이디
	 * @param orderId          주문 아이디
	 * @param cancleRequestDto 요청 취소 DTO
	 * @return
	 */
	@UserRequired(vaild = UserType.OWNER)
	@PostMapping("/{orderId}")
	public ResponseEntity<MessageDto> cancleOrder(
		@SessionAttribute(name = Const.LOGIN_USER) Long userId,
		@PathVariable Long orderId,
		@RequestBody CancleRequestDto cancleRequestDto
	) {
		//주문 접근해서 취소 상태로 변경
		orderService.cancleOrder(orderId, userId, cancleRequestDto);

		return new ResponseEntity<>(new MessageDto("주문이 취소되었습니다."), HttpStatus.OK);
	}

	@ExceptionHandler({NoExistException.class})
	public ResponseEntity<ExceptionResponse> noExistExceptionHandleException(Exception e) {

		//Map 생성(키랑 값 저장)
		Map<String, String> errors = new HashMap<>();

		//errors에 이름과 에러 메세지를 추가
		errors.put("NO_EXIST_EXCEPTION", e.getMessage());

		//정보 담을 객체 생성(상태코드, 코드 값, 에러 정보)
		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND,
			HttpStatus.NOT_FOUND.value(), errors);

		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<ExceptionResponse> illegalArgumentExceptionHandleException(Exception e) {

		//Map 생성(키랑 값 저장)
		Map<String, String> errors = new HashMap<>();

		//errors에 이름과 에러 메세지를 추가
		errors.put("ILLEGAL_ARGUMENT", e.getMessage());

		//정보 담을 객체 생성(상태코드, 코드 값, 에러 정보)
		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST,
			HttpStatus.BAD_REQUEST.value(), errors);

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
