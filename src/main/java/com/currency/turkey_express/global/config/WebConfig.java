package com.currency.turkey_express.global.config;

import com.currency.turkey_express.global.converter.CookieCartDataConverter;
import com.currency.turkey_express.global.interceptor.LoginInterceptor;
import com.currency.turkey_express.global.interceptor.UserInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final LoginInterceptor loginInterceptor;
	private final UserInterceptor userInterceptor;
	private final CookieCartDataConverter cookieCartDataConverter;
//	public WebConfig(LoginInterceptor loginInterceptor, UserInterceptor userInterceptor) {
//		this.loginInterceptor = loginInterceptor;
//		this.userInterceptor = userInterceptor;
//	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor);
		registry.addInterceptor(userInterceptor);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(cookieCartDataConverter);
	}

}
