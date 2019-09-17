package com.platform.system.common.exception.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 带有该注解的地方,访问时,随机抛出异常(用于测试使用)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RandomlyThrowsException {
}
