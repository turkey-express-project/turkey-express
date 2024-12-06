package com.currency.turkey_express.global.converter;

import com.currency.turkey_express.domain.cart.dto.CartCookieDto;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CookieCartDataConverter implements Converter<String, CartCookieDto> {

	private final ObjectMapper objectMapper;

	public CookieCartDataConverter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public CartCookieDto convert(String source) {

		CartCookieDto cartCookieDto = null;

		try {

			cartCookieDto = objectMapper.readValue(source, CartCookieDto.class);

		} catch (JsonProcessingException e) {
			throw new BusinessException(ExceptionType.JSON_PARSE_ERROR);
		}

		return cartCookieDto;
	}
}
