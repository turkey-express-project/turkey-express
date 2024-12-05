package com.currency.turkey_express.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {

	EXIST_USER(HttpStatus.BAD_REQUEST, "동일한 email의 사용자가 존재합니다."),
	USER_NOT_MATCH(HttpStatus.BAD_REQUEST, "잘못된 유저의 정보에 접근하고 있습니다."),
	EMAIL_NOT_MATCH(HttpStatus.BAD_REQUEST, "잘못된 이메일 입니다. 다시 입력해주세요."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),

	MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 메뉴를 찾을 수 없습니다."),
	STORE_NOT_FOUND(HttpStatus.NOT_FOUND,"스토어를 찾을 수 없습니다."),
	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "해당 작업을 수행할 권한이 없습니다."),
	INVALID_MENU_DATA(HttpStatus.BAD_REQUEST, "메뉴 데이터가 잘못되었습니다.");

	private final HttpStatus httpStatus;
	private final String errorMessage;

	ExceptionType(HttpStatus httpStatus, String errorMessage) {
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}
}
