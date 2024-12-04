package com.currency.turkey_express.domain.user.contorller;

import com.currency.turkey_express.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserContoroller {

	private final UserService userService;

}
