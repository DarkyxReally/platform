package com.platform.auth.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * REST请求记录
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface RestLog {
    
    /**
     * 系统code
     * @return
     */
    String systemCode() default "";
    
    /**
     * 模块
     * @return
     */
    String moduleCode() default "";
    
    /**
     * 描述
     * @return
     */
    String description();
    
}
