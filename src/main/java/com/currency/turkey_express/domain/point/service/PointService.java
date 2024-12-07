package com.currency.turkey_express.domain.point.service;

import com.currency.turkey_express.domain.point.dto.PointResponseDto;
import com.currency.turkey_express.domain.point.repository.PointRepository;
import com.currency.turkey_express.domain.user.repository.UserRepository;
import com.currency.turkey_express.global.base.entity.Point;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

	private final PointRepository pointRepository;
	private final UserRepository userRepository;

	/**
	 * 고객별 포인트 조회 API
	 */
	public List<PointResponseDto> findAllPointList(Long userId, Long loginUserId) {
		//사용자 id 확인
		User user = userRepository.findByIdOrElseThrow(userId);

		//로그인한 사용자랑 ID가 일치하는지 확인
		if (!userId.equals(loginUserId)) {
			throw new BusinessException(ExceptionType.USER_NOT_MATCH);
		}

		//특정 유저 포인트 조회
		List<Point> points = pointRepository.findAllByUserId(userId);

		List<PointResponseDto> pointResponseDtoList = new ArrayList<>();

		for (Point newPoint : points) {
			//Point 객체 -> PointResponseDto 객체로 변환
			PointResponseDto pointResponseDto = PointResponseDto.toDto(newPoint);
			pointResponseDtoList.add(pointResponseDto);
		}

		return pointResponseDtoList;
	}
}
