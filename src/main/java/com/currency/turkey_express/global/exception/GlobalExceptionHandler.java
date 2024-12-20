package com.currency.turkey_express.global.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// BusinessException에서 발생한 에러(비지니스 로직)
	@ExceptionHandler({BusinessException.class})
	public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException exception) {
		//BusinessException에서 ExceptionType을 가져오기(상태코드 & 에러메세지)
		ExceptionType exceptionType = exception.getExceptionType();

		//Map 생성(키랑 값 저장)
		Map<String, String> errors = new HashMap<>();
		//errors에 이름과 에러 메세지를 추가
		errors.put(exceptionType.name(), exceptionType.getErrorMessage());

		//정보 담을 객체 생성(상태코드, 코드 값, 에러 정보)
		ExceptionResponse exceptionResponse = new ExceptionResponse(exceptionType.getHttpStatus(),
			exceptionType.getHttpStatus().value(), errors);

		//로그 기록
		log.error("[ {} ] {} : {}", exception.getClass(), exceptionType.getHttpStatus(),
			exceptionResponse.getErrors());

		//반환
		return new ResponseEntity<>(exceptionResponse, exceptionType.getHttpStatus());
	}

	//유효성 검사 예외처리
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidExceptions(
		MethodArgumentNotValidException exception) {

		//유효성 검사 실패한 에러 정보 Map 저장
		Map<String, String> errors = new HashMap<>();
		for (FieldError error : exception.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}

		//정보 담을 객체 생성(상태코드, 코드 값, 에러 정보)
		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST,
			HttpStatus.BAD_REQUEST.value(), errors);

		//로그 기록
		log.error("[MethodArgumentNotValidException] : {}", exceptionResponse.getErrors());

		//반환
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
