package com.platform.system.common.aspect;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import com.platform.system.common.config.properties.DelayProperties;
import com.platform.system.common.web.annotations.Delay;


/**
 * 延迟返回结果切面
 */
@Aspect
public class DelayReturnAspect implements Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(DelayReturnAspect.class);
    private final int order;
    private final DelayProperties delayProperties;

    public DelayReturnAspect(int order, DelayProperties delayProperties) {
        this.order = order;
        this.delayProperties = delayProperties;
    }

    @Around("@annotation(com.platform.system.common.web.annotations.Delay)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object result = joinPoint.proceed();
        try{
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;    
            Method targetMethod = methodSignature.getMethod();    
            Delay delay = targetMethod.getAnnotation(Delay.class);
            if (null == delay){
                Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), targetMethod.getParameterTypes());
                delay = realMethod.getAnnotation(Delay.class);
            }
            long timeInMillseconds = delayProperties.getTimeInMillseconds();
            if (delay != null){
                // 注解上指定时间
                int value = delay.value();
                if (value > 0){
                    timeInMillseconds = value;
                }
            }
            if (timeInMillseconds != 0L) {
                LOGGER.debug("method {} was made delay {} mills to return result", joinPoint.getSignature(), timeInMillseconds);
                TimeUnit.MILLISECONDS.sleep(timeInMillseconds);
            }
        }catch(Exception e){
            // 忽略异常
        }
        return result;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
