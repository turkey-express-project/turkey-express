package com.currency.turkey_express.global.interceptor;

import com.currency.turkey_express.global.annotation.LoginRequired;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.constant.Const;
import com.currency.turkey_express.global.exception.ExceptionType;
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
			throw new UnauthenticatedException(ExceptionType.NOT_LOGIN);
		}

		//----추가한 코드
		//세션에서 로그인된 사용자 정보 가져오기
		User user = (User) session.getAttribute(Const.LOGIN_USER);

		//로그인된 사용자 ID를 HttpServletRequest에 저장
		request.setAttribute("loginUserId", user.getId());

		return true;

	}
}
