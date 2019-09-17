package com.platform.system.common.web.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略XSS校验
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface IgnoreXss {
}
