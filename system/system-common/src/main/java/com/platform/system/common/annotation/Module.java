package com.platform.system.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模块
 * @version: 1.0.4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.METHOD})
public @interface Module {
    
    /**
     * 模块code
     * @version: 1.0.4
     * @return
     */
    String code();
}
