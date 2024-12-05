package com.currency.turkey_express.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

	private ExceptionType exceptionType;

	public BusinessException(ExceptionType exceptionType) {
	}
}
