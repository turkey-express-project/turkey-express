package com.currency.turkey_express.domain.user.service;

import com.currency.turkey_express.domain.user.dto.UserResponseDto;
import com.currency.turkey_express.domain.user.repository.UserRepository;
import com.currency.turkey_express.global.base.enums.user.UserType;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	//회원가입
	public UserResponseDto signUp(String email, String userNickname, String password,
		UserType userType) throws IOException {
		//email 확인
	}
}
