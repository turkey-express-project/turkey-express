package com.currency.turkey_express.global.exception;

import lombok.Getter;

@Getter
public class UnauthenticatedException extends BusinessException {
	
	public UnauthenticatedException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
