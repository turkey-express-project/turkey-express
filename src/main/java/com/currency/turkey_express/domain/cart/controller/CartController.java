package com.currency.turkey_express.domain.cart.controller;

import com.currency.turkey_express.domain.cart.dto.CartCookieDto;
import com.currency.turkey_express.domain.cart.dto.CartMenuResponseDto;
import com.currency.turkey_express.domain.cart.dto.CartRequestDto;
import com.currency.turkey_express.domain.cart.exception.NoExistException;
import com.currency.turkey_express.domain.cart.service.CartService;
import com.currency.turkey_express.global.annotation.UserRequired;
import com.currency.turkey_express.global.base.dto.MessageDto;
import com.currency.turkey_express.global.base.enums.user.UserType;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionResponse;
import com.currency.turkey_express.global.exception.ExceptionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {

	private final ObjectMapper objectMapper;

	private final CartService cartService;

	/**
	 * 카트에 메뉴 담기 API
	 *
	 * @param cartCookieDto
	 * @param response
	 * @param cartRequestDto
	 * @return
	 */
	@UserRequired(vaild = UserType.CUSTOMER)
	@PostMapping("")
	public ResponseEntity<MessageDto> addMenu(
		@CookieValue(value = "CART", required = false) CartCookieDto cartCookieDto,
		HttpServletResponse response,
		@RequestBody CartRequestDto cartRequestDto
	) {

		if (cartCookieDto == null) {
			cartCookieDto = new CartCookieDto();
		}

		// 요청으로 주어진 메뉴와 옵셥 아이디에 대한 데이터 가져오기

		CartMenuResponseDto cartMenuResponseDto = cartService.getSelectedMenuInfo(cartRequestDto);

		// 카트 쿠키 데이터 객체에 메뉴 추가하기

		cartCookieDto.addMenu(
			cartMenuResponseDto.getMenu(),
			cartMenuResponseDto.getMenuOptionSets(),
			cartRequestDto.getMenuCount()
		);

		// 카트 쿠키 데이터 객체를 응답 쿠키에 삽입

		try {

			Cookie cartContentCookie = new Cookie("CART",
				URLEncoder.encode(objectMapper.writeValueAsString(cartCookieDto),
					StandardCharsets.UTF_8));

			cartContentCookie.setMaxAge(60 * 60 * 24);
			cartContentCookie.setPath("/");

			response.addCookie(cartContentCookie);

		} catch (Exception e) {
			throw new BusinessException(ExceptionType.JSON_PARSE_ERROR);
		}

		return new ResponseEntity<>(new MessageDto("카트에 주문이 등록됐습니다"), HttpStatus.OK);
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
		errors.put("NO_EXIST_EXCEPTION", e.getMessage());

		//정보 담을 객체 생성(상태코드, 코드 값, 에러 정보)
		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST,
			HttpStatus.BAD_REQUEST.value(), errors);

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
