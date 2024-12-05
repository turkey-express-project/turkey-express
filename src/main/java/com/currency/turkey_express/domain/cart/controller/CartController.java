package com.currency.turkey_express.domain.cart.controller;

import com.currency.turkey_express.domain.cart.dto.CartCookieDto;
import com.currency.turkey_express.domain.cart.dto.CartMenuResponseDto;
import com.currency.turkey_express.domain.cart.dto.CartRequestDto;
import com.currency.turkey_express.domain.cart.service.CartService;
import com.currency.turkey_express.global.base.dto.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	 * @param request
	 * @param response
	 * @param cartRequestDto
	 * @return
	 */
	/* TODO @LoginRequired */
	@PostMapping("")
	public ResponseEntity<MessageDto> addMenu(
		HttpServletRequest request,
		HttpServletResponse response,
		@RequestBody CartRequestDto cartRequestDto
	) {

		/* 쿠키 목록에서 이름이 'CART'인 쿠키의 값 추출
		 * 'CART' 쿠키 값은 UTF-8로 인코딩 된 상태
		 * */

		Cookie cartContentCookie = null;

		String encodedCartValue = null;

		if (request.getCookies() != null) {

			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("CART")) {

					cartContentCookie = cookie;
					encodedCartValue = cartContentCookie.getValue();

					break;
				}
			}
		}

		/* 인코딩 된 문자열 디코딩 후 Jackson으로 CartCookieDto 객체(카트 쿠키 데이터 객체)로 변환
		 * */

		CartCookieDto cartCookieDto = new CartCookieDto();

		if (encodedCartValue != null) {

			try {
				String decodedCartValue = URLDecoder.decode(encodedCartValue,
					StandardCharsets.UTF_8);

				log.info("현제 카트 내부 데이터 : %n{}", decodedCartValue);

				cartCookieDto = objectMapper.readValue(decodedCartValue, CartCookieDto.class);

			} catch (Exception e) {

				Cookie cookie = new Cookie("CART", null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);

				throw new RuntimeException("쿠키 변환에 문제가 발생했습니다.");
			}

		} else {
			cartCookieDto = new CartCookieDto();
		}


		/* 요청으로 주어진 메뉴와 옵셥 아이디에 대한 데이터 가져오기
		 * */

		CartMenuResponseDto cartMenuResponseDto = cartService.getSelectedMenuInfo(cartRequestDto);

		/* 카트 쿠키 데이터 객체에 메뉴 추가하기
		 * */

		cartCookieDto.addMenu(
			cartMenuResponseDto.getMenu(),
			cartMenuResponseDto.getMenuOptionSets(),
			cartRequestDto.getMenuCount()
		);


		/* 카트 쿠키 데이터 객체를 응답 쿠키에 삽입
		 * */
		try {

			cartContentCookie = new Cookie("CART",
				URLEncoder.encode(objectMapper.writeValueAsString(cartCookieDto), "UTF-8"));

			cartContentCookie.setMaxAge(60 * 60 * 24);
			cartContentCookie.setPath("/");

		} catch (Exception e) {
			throw new RuntimeException("장바구니 메뉴 추가에 문제가 발생했습니다.");
		}

		response.addCookie(cartContentCookie);

		return new ResponseEntity<>(new MessageDto("카트에 주문이 등록됐습니다"), HttpStatus.OK);
	}
}
