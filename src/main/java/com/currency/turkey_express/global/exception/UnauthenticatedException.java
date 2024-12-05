package com.currency.turkey_express.global.exception;

import lombok.Getter;

@Getter
public class UnauthenticatedException extends RuntimeException {

	public UnauthenticatedException(final ExceptionType exceptionType) {
		super();
	}
}
