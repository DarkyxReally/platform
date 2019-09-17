package com.platform.system.common.aspect;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.core.Ordered;

import com.platform.system.common.trace.TraceProperties;

/**
 * 溯源切面
 * 主要逻辑: 拦截自定义的溯源注解或者异步执行的注解, 在执行前往MDC增加溯源id, 执行结束后, 移除溯源id
 * @version: 1.0
 */
@Aspect
public class TraceAspect implements Ordered {

    private final int order;

    private TraceProperties traceProperties;

    public TraceAspect(int order, TraceProperties traceProperties) {
        this.order = order;
        this.traceProperties = traceProperties;
    }

    /**
     * 拦截异步或者是自定义的溯源注解
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(org.springframework.scheduling.annotation.Async) "
            + "|| @annotation(com.platform.system.common.trace.annotations.Trace) "
            + "|| @annotation(org.springframework.scheduling.annotation.Scheduled)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        String traceKey = null;
        try{
            traceKey = traceProperties.getKey();
            if(StringUtils.isBlank(traceKey)){
                traceKey = TraceProperties.TRACE_ID;
            }
            String traceId = MDC.get(traceKey);
            if(StringUtils.isBlank(traceId)){
                MDC.put(traceKey, UUID.randomUUID().toString().replace("-", ""));
            }
        }catch(Exception e){
            // 忽略异常
        }
        
        try{
            // 执行真实请求
            return joinPoint.proceed();
        }
        finally{
            if(StringUtils.isNotBlank(traceKey)){
                MDC.remove(traceKey);
            }
        }
    }

    @Override
    public int getOrder(){
        return order;
    }
}
