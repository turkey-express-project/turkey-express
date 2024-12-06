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
	NOT_LOGIN(HttpStatus.UNAUTHORIZED, "사용자 세션이 존재하지 않습니다. 로그인을 해주세요."),
	STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다."),
	CLOSE_STORE(HttpStatus.NOT_FOUND, "해당 가게는 폐업되었습니다.."),
	SELF_ADD_FAVORITE(HttpStatus.BAD_REQUEST, "자신의 가게는 즐겨찾기 할 수 없습니다."),
	MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 메뉴를 찾을 수 없습니다."),
	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "해당 작업을 수행할 권한이 없습니다."),
	INVALID_MENU_DATA(HttpStatus.BAD_REQUEST, "메뉴 데이터가 잘못되었습니다."),
	TOP_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "상위 카테고리를 찾을 수 없습니다."),
	SUB_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "하위 카테고리를 찾을 수 없습니다."),
	ALREADY_FULL_STORE(HttpStatus.BAD_REQUEST,"한 계정당 최대 3개의 가게를 운영할 수 있습니다."),
	FAVORITE_NOT_FOUND(HttpStatus.NOT_FOUND,"즐겨찾기가 존재하지 않습니다."),
	INVALID_RATING(HttpStatus.BAD_REQUEST, "별점은 1~5 사이의 숫자만 입력 가능합니다."),
	DUPLICATE_REVIEW(HttpStatus.BAD_REQUEST, "리뷰는 주문 1건당 1회만 작성할 수 있습니다."),
	ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "생성된 주문이 없습니다."),
	JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST, "장바구니 데이터에 문제가 있습니다.");


	private final HttpStatus httpStatus;
	private final String errorMessage;

	ExceptionType(HttpStatus httpStatus, String errorMessage) {
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}
}
