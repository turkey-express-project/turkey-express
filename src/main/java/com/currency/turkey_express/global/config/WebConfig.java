package com.currency.turkey_express.global.config;

import com.currency.turkey_express.global.interceptor.LoginInterceptor;
import com.currency.turkey_express.global.interceptor.UserRoleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private final LoginInterceptor loginInterceptor;
	private final UserRoleInterceptor userRoleInterceptor;

	public WebConfig(LoginInterceptor loginInterceptor, UserRoleInterceptor userRoleInterceptor) {
		this.loginInterceptor = loginInterceptor;
		this.userRoleInterceptor = userRoleInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor);
		registry.addInterceptor(userRoleInterceptor);
	}

}
