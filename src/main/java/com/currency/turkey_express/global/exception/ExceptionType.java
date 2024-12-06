package com.currency.turkey_express.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {

	EXIST_USER(HttpStatus.BAD_REQUEST, "동일한 email의 사용자가 존재합니다."),
	EMAIL_NOT_MATCH(HttpStatus.BAD_REQUEST, "잘못된 이메일 입니다. 다시 입력해주세요."),
	USER_NOT_MATCH(HttpStatus.BAD_REQUEST, "유저의 정보가 일치하지 않습니다."),
	DELETED_USER(HttpStatus.BAD_REQUEST, "해당 유저는 이미 탈퇴되었습니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
	PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	ALREADY_LOGGED_IN(HttpStatus.UNAUTHORIZED, "이미 로그인 되어있는 사용자입니다."),
	NOT_LOGIN(HttpStatus.UNAUTHORIZED, "사용자 세션이 존재하지 않습니다. 로그인을 해주세요.");

	private final HttpStatus httpStatus;
	private final String errorMessage;

	ExceptionType(HttpStatus httpStatus, String errorMessage) {
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}
}
