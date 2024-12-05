package com.currency.turkey_express.domain.user.service;

import com.currency.turkey_express.domain.user.dto.UserResponseDto;
import com.currency.turkey_express.domain.user.repository.UserRepository;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.base.enums.user.UserStatus;
import com.currency.turkey_express.global.base.enums.user.UserType;
import com.currency.turkey_express.global.config.PasswordEncoder;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	//회원가입
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

	//로그인
//	public User login(String email, String password) {
//	}
}
