package com.platform.system.common.web.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 延迟注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Delay {

    /**
     * 延长多长时间单位毫秒
     * @version: 1.0
     * @return
     */
    int value() default 0;
}
