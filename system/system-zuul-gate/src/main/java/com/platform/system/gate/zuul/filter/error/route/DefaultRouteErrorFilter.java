package com.platform.system.gate.zuul.filter.error.route;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import com.netflix.client.ClientException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.exception.ExceptionUtils;

/**
 * PRE异常处理Filte基类
 * @version: 1.0
 */
@Slf4j
public class DefaultRouteErrorFilter extends BaseRouteErrorFilter {

    public DefaultRouteErrorFilter(int filterOrder) {
        super(filterOrder);
    }
    
    @Override
    protected boolean shouldFilter(ZuulException e) {
        RequestContext ctx = RequestContext.getCurrentContext();
        return null != ctx.getThrowable();
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        Throwable cause = ExceptionUtils.getCause(throwable, ClientException.class);
        if (cause instanceof ClientException){
            log.error("后端服务异常:{}", cause.getMessage());
            sendErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, StatusCode.SERVER_UNKNOWN_ERROR, "后端服务异常");
        }else{
            cause = ExceptionUtils.getCause(throwable, HystrixRuntimeException.class);
            if (cause instanceof HystrixRuntimeException){
                log.info("后端服务异常, 熔断机制已启用:{}", cause.getMessage());
            }else{
                log.error("后端服务异常,{}", throwable);
            }
            sendErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, StatusCode.SERVER_UNKNOWN_ERROR, "后端服务异常, 请稍后重试");
        }
        return null;
    }
}
