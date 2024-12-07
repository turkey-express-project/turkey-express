package com.currency.turkey_express.domain.point.controller;


import com.currency.turkey_express.domain.point.dto.PointResponseDto;
import com.currency.turkey_express.domain.point.service.PointService;
import com.currency.turkey_express.global.annotation.UserRequired;
import com.currency.turkey_express.global.base.enums.user.UserType;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("users/{userId}/points")
public class PointController {

	private final PointService pointService;

	/**
	 * 고객별 포인트 조회 API
	 */
	@UserRequired(vaild = UserType.CUSTOMER)
	@GetMapping
	public ResponseEntity<List<PointResponseDto>> findAllPointList(@PathVariable Long userId,
		HttpServletRequest httpServletRequest) throws IOException {

		//인터셉터에서 로그인된 사용자 ID 가져오기
		Long loginUserId = (Long) httpServletRequest.getAttribute("loginUserId");

		//콘솔 로그 확인
		log.info("userId - PathVariable: {}", userId);
		log.info("loginUserId - HttpServletRequest: {}", loginUserId);

		List<PointResponseDto> pointResponseDto =
			pointService.findAllPointList(
				userId,
				loginUserId
			);

		return new ResponseEntity<>(pointResponseDto, HttpStatus.OK);
	}


}
