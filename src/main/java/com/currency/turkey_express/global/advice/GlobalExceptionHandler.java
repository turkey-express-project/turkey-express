package com.currency.turkey_express.global.advice;

import com.currency.turkey_express.global.exception.UnauthenticatedException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({UnauthenticatedException.class})
	public ResponseEntity<Map<String, String>> unauthenticatedExceptionHandler(
		RuntimeException ex) {

		Map<String, String> response = new HashMap<>();
		response.put("error_msg", ex.getMessage());

		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(response);
	}

}
