package com.platform.system.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.METHOD})
public @interface PlatformSystem {
    
    /**
     * 系统code
     * @version: 1.0
     * @return
     */
    String code();
}
