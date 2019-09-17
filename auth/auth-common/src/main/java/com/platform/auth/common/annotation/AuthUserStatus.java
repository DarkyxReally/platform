package com.platform.auth.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.platform.system.common.context.user.AuthUserTypeConstant.AuthUserType;


/**
 * 认证用户状态
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface AuthUserStatus {

    /**
     * 是否需要存在用户信息, 默认必须存在用户信息
     * @return
     */
    boolean requireUser() default true;
    
    /**
     * 认证特定的用户类别
     * @return
     */
    AuthUserType[] userType() default {}; 
}
