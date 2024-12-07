package com.currency.turkey_express.domain.user.service;

import com.currency.turkey_express.domain.point.repository.PointRepository;
import com.currency.turkey_express.domain.user.dto.UserDeleteRequestDto;
import com.currency.turkey_express.domain.user.dto.UserResponseDto;
import com.currency.turkey_express.domain.user.repository.UserRepository;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.base.enums.user.UserStatus;
import com.currency.turkey_express.global.base.enums.user.UserType;
import com.currency.turkey_express.global.config.PasswordEncoder;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final PointRepository pointRepository;

	/**
	 * 회원가입 API
	 */
	@Transactional
	public UserResponseDto signUp(String email, String userNickname, String password,
		UserType userType)
		throws IOException {

		//email 중복 검사
		if (userRepository.existsByEmail(email)) {
			throw new BusinessException(ExceptionType.EXIST_USER);
		}

		//비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(password);

		User user = new User(email, userNickname, encodedPassword, userType, UserStatus.REGISTER);
		User savedUser = userRepository.save(user);

		return UserResponseDto.toDto(savedUser);
	}

	/**
	 * 로그인 API - 로그인시 totalPoint 자동으로 업데이트
	 */
	public User login(String email, String password) {

		//사용자 email 확인
		User user = userRepository.findByEmailOrElseThrow(email);

		//탈퇴 확인
		if (user.getUserStatus().equals(UserStatus.DELETE)) {
			throw new BusinessException(ExceptionType.DELETED_USER);
		}

		//비밀번호 일치하는지 확인
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new BusinessException(ExceptionType.PASSWORD_NOT_MATCH);
		}

		//유저 포인트 합산하기
		BigDecimal totalPoints = pointRepository.getTotalPointsByUserId(user.getId());

		//포인트 업데이트
		user.updateTotalPoint(totalPoints);

		//업데이트된 user 저장
		userRepository.save(user);

		//포인트 로그 확인
		log.info("totalPoints : {}", totalPoints);

		return user;
	}

	//회원탈퇴
	@Transactional
	public UserResponseDto userDelete(Long userId, Long loginUserId,
		UserDeleteRequestDto userDeleteRequestDto) {

		//사용자 id 확인
		User user = userRepository.findByIdOrElseThrow(userId);

		//로그인한 사용자랑 ID가 일치하는지 확인
		if (!userId.equals(loginUserId)) {
			throw new BusinessException(ExceptionType.USER_NOT_MATCH);
		}

		//비밀번호 확인
		if (!passwordEncoder.matches(user.getPassword(), userDeleteRequestDto.getPassword())) {
			throw new BusinessException(ExceptionType.PASSWORD_NOT_MATCH);
		}

		// 탈퇴 확인
		if (user.getUserStatus().equals(UserStatus.DELETE)) {
			throw new BusinessException(ExceptionType.DELETED_USER);
		}

		//탈퇴일 업데이트
		user.updateLeavedAt(LocalDateTime.now());

		//유저 상태 변경
		user.updateUserStatus(UserStatus.DELETE);

		return new UserResponseDto(user);
	}

	/**
	 * 유저 단건 조회 API
	 */
	public UserResponseDto getUser(Long userId, Long loginUserId) {
		//사용자 id 확인
		User user = userRepository.findByIdOrElseThrow(userId);

		//로그인한 사용자랑 ID가 일치하는지 확인
		if (!userId.equals(loginUserId)) {
			throw new BusinessException(ExceptionType.USER_NOT_MATCH);
		}

		return UserResponseDto.toDto(user);
	}
}
