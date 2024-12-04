package com.currency.turkey_express.domain.user.contorller;

import com.currency.turkey_express.domain.user.dto.SignUpRequestDto;
import com.currency.turkey_express.domain.user.dto.UserResponseDto;
import com.currency.turkey_express.domain.user.service.UserService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserContoroller {

	private final UserService userService;

	//회원가입
	@PostMapping("/signup")
	public ResponseEntity<UserResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto)
		throws IOException {
		UserResponseDto userResponseDto = userService.signUp(
			signUpRequestDto.getEmail(),
			signUpRequestDto.getUserNickname(),
			signUpRequestDto.getPassword(),
			signUpRequestDto.getUserType()
		);

		return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
	}
}
