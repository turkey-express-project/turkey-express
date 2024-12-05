package com.currency.turkey_express.domain.user.contorller;

import com.currency.turkey_express.domain.user.dto.LoginRequestDto;
import com.currency.turkey_express.domain.user.dto.SignUpRequestDto;
import com.currency.turkey_express.domain.user.dto.UserDeleteRequestDto;
import com.currency.turkey_express.domain.user.dto.UserResponseDto;
import com.currency.turkey_express.domain.user.service.UserService;
import com.currency.turkey_express.global.annotation.LoginRequired;
import com.currency.turkey_express.global.base.dto.MessageDto;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.constant.Const;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserContoroller {

	private final UserService userService;

	//회원가입
	@PostMapping("/signup")
	public ResponseEntity<UserResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto)
		throws IOException {

		//콘솔 로그 확인
		log.info("Email: {}", signUpRequestDto.getEmail());
		log.info("UserNickname: {}", signUpRequestDto.getUserNickname());
		log.info("Password: {}", signUpRequestDto.getPassword());
		log.info("UserType: {}", signUpRequestDto.getUserType());

		UserResponseDto userResponseDto = userService.signUp(
			signUpRequestDto.getEmail(),
			signUpRequestDto.getUserNickname(),
			signUpRequestDto.getPassword(),
			signUpRequestDto.getUserType()
		);

		return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
	}

	//로그인
	@PostMapping("/login")
	public ResponseEntity<MessageDto> login(@RequestBody LoginRequestDto loginRequestDto,
		HttpServletRequest httpServletRequest)
		throws IOException {

		//콘솔 로그 확인
		log.info("Email: {}", loginRequestDto.getEmail());
		log.info("Password: {}", loginRequestDto.getPassword());

		User user = userService.login(
			loginRequestDto.getEmail(),
			loginRequestDto.getPassword()
		);

		//사용자 session 존재하면 가져오기 세션 없으면 null 반환
		HttpSession session = httpServletRequest.getSession(true );

		//중복 로그인 방지
		//session 로그인된 사용자 정보 가져옴
		if (session.getAttribute(Const.LOGIN_USER) != null) {
			throw new BusinessException(ExceptionType.ALREADY_LOGGED_IN);
		}

		//세션에 저장
		session.setAttribute(Const.LOGIN_USER, user);

		MessageDto response = new MessageDto("로그인이 완료되었습니다.");

		return ResponseEntity.ok(response);
	}

	//회원탈퇴
	@LoginRequired
	@PatchMapping("/{userId}")
	public ResponseEntity<MessageDto> userDelete(@PathVariable Long userId,
		@RequestBody UserDeleteRequestDto userDeleteRequestDto,
		HttpServletRequest httpServletRequest
	) throws IOException {

		//인터셉터에서 로그인된 사용자 ID 가져오기
		Long loginUserId = (Long) httpServletRequest.getAttribute("loginUserId");

		//콘솔 로그 확인
		log.info("userId - PathVariable: {}", userId);
		log.info("Password: {}", userDeleteRequestDto.getPassword());
		log.info("loginUserId - HttpServletRequest: {}", loginUserId);

		UserResponseDto userResponseDto = userService.userDelete(
			userId,
			userDeleteRequestDto.getPassword(),
			loginUserId
		);

		MessageDto response = new MessageDto("회원 탈퇴 완료되었습니다.");

		return ResponseEntity.ok(response);
	}
}
