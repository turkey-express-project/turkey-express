package com.currency.turkey_express.global.annotation;

import com.currency.turkey_express.global.base.enums.user.UserType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserRequired {
	UserType userType() default UserType.CUSTOMER;
}
