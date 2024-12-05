package com.currency.turkey_express.global.interceptor;

import com.currency.turkey_express.global.annotation.LoginRequired;
import com.currency.turkey_express.global.constant.Const;
import com.currency.turkey_express.global.exception.UnauthenticatedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) {

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		LoginRequired loginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);

		if (loginRequired == null) {
			return true;
		}

		// 로그인 확인 -> 로그인하면 session에 값이 저장되어 있다는 가정.
		// 세션이 존재하면 가져온다. 세션이 없으면 session = null
		HttpSession session = request.getSession(false);

		// 로그인하지 않은 사용자인 경우
		if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
			throw new UnauthenticatedException("로그인 해주세요.");
		}

		return true;

	}
}
