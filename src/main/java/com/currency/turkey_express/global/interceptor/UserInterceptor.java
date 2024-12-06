package com.currency.turkey_express.global.interceptor;

import com.currency.turkey_express.global.annotation.UserRequired;
import com.currency.turkey_express.global.base.entity.User;
import com.currency.turkey_express.global.base.enums.user.UserStatus;
import com.currency.turkey_express.global.base.enums.user.UserType;
import com.currency.turkey_express.global.constant.Const;
import com.currency.turkey_express.global.exception.BusinessException;
import com.currency.turkey_express.global.exception.ExceptionType;
import com.currency.turkey_express.global.exception.UnauthenticatedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	){
		//핸들러 사용, 어노테이션 받아옴
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		UserRequired userRequired = handlerMethod.getMethodAnnotation(UserRequired.class);

		//어노테이션이 존재하지 않는다면 true 반환
		if(userRequired == null){
			return true;
		}

		//세션 받아옴
		HttpSession session = request.getSession(false);

		// 로그인하지 않은 사용자인 경우
		if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
			log.info("LoginInterceptor : 사용자 세션이 존재하지 않습니다.");
			throw new UnauthenticatedException(ExceptionType.NOT_LOGIN);
		}

		//세션에서 유저 엔티티 생성
		User user = (User) session.getAttribute(Const.LOGIN_USER);
		if (user.getUserStatus().equals(
			UserStatus.DELETE)
		){
			throw new BusinessException(ExceptionType.DELETED_USER);
		}

		//만약 어노테이션 속성 값이 UserType.OWNER 라면 owner 검증 로직 실행
		if (userRequired.vaild().equals(UserType.OWNER.toString())){
			if (user.getUserType().equals(
				UserType.CUSTOMER)
			){
				throw new BusinessException(ExceptionType.UNAUTHORIZED_ACCESS);
			}
		}
		//만약 어노테이션 속성 값이 CUSTOMER 라면 customer 검증 로직 실행
		if (userRequired.vaild().equals(UserType.CUSTOMER.toString())){
			if (user.getUserType().equals(
				UserType.OWNER)
			){
				throw new BusinessException(ExceptionType.UNAUTHORIZED_ACCESS);
			}
		}

		request.setAttribute("loginUserId", user.getId());
		return true;
	}
}
