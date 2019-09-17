package com.platform.system.common.aspect;

import java.security.SecureRandom;
import java.util.Random;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;

import com.platform.system.common.config.properties.ManualExceptionProperties;
import com.platform.system.common.exception.ManualException;


/**
 * 手动异常切面
 */
@Aspect
public class ManualExceptionAspect implements Ordered {
    private final int order;
    private final ManualExceptionProperties properties;
    private static final Random RANDOM = new SecureRandom();

    public ManualExceptionAspect(int order, ManualExceptionProperties properties) {
        this.order = order;
        this.properties = properties;
    }

    @Around("@annotation(com.platform.system.common.exception.annotations.RandomlyThrowsException)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        if (properties.isEnabled()) {
            if (RANDOM.nextInt(100) % properties.getFactor() == 0) {
                throw new ManualException("manual exception");
            }
        }
        return joinPoint.proceed();
    }

    @Override
    public int getOrder() {
        return order;
    }
}
