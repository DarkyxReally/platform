package com.platform.system.common.aspect;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;

import com.platform.system.common.constant.RequestAttributeConst;
import com.platform.system.common.web.ServletContextHolder;


/**
 * 生成请求ID切面
 */
@Aspect
public class RequestIdStuffAspect implements Ordered {
    private final int order;

    public RequestIdStuffAspect(int order) {
        this.order = order;
    }

    @Before(value = "within(com.platform.app..*) " +
            "&& (@annotation(org.springframework.web.bind.annotation.ResponseBody)" +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public void before(JoinPoint joinPoint) {
        final String requestId = ServletContextHolder.fetchRequestId();
        final HttpServletResponse response = ServletContextHolder.getResponse();
        if (response != null && response.getHeader(RequestAttributeConst.REQUEST_ID) == null) {
            response.addHeader(RequestAttributeConst.REQUEST_ID, requestId);
        }
    }

    @Override
    public int getOrder() {
        return order;
    }
}
