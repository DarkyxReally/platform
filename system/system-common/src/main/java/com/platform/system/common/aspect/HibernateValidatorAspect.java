package com.platform.system.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.validation.BindingResult;

import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.rest.RestStatus;
import com.platform.system.common.util.HibernateValidators;


/**
 * hibernate校验切面
 * @see RequestLogging
 * @see org.springframework.web.bind.annotation.ControllerAdvice
 */
@Aspect
public class HibernateValidatorAspect implements Ordered {

    private final int order;
    private final RestStatus throwIfInvalidModel;

    public HibernateValidatorAspect() {
        this(Byte.MAX_VALUE);
    }

    public HibernateValidatorAspect(int order) {
        this(order, StatusCode.INVALID_MODEL_FIELDS);
    }

    public HibernateValidatorAspect(int order, RestStatus throwIfInvalidModel) {
        this.order = order;
        this.throwIfInvalidModel = throwIfInvalidModel;
    }

    /**
     * 校验框架切面
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "within(com.platform..*) "
            + "&& (@annotation(org.springframework.web.bind.annotation.ResponseBody)"
            + "|| @annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable{
        for(Object arg : joinPoint.getArgs()){
            if(arg instanceof BindingResult){
                HibernateValidators.throwIfInvalidModel((BindingResult)arg, throwIfInvalidModel);
            }
        }
        return joinPoint.proceed();
    }

    @Override
    public int getOrder(){
        return order;
    }
}
