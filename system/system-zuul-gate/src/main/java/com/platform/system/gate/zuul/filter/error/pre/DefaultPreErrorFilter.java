package com.platform.system.gate.zuul.filter.error.pre;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.StatusCode;

/**
 * 默认的Pre异常处理
 */
@Slf4j
public class DefaultPreErrorFilter extends BasePreErrorFilter {

    public DefaultPreErrorFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    protected boolean shouldFilter(ZuulException cause) {
        RequestContext ctx = RequestContext.getCurrentContext();
        return null != ctx.getThrowable();
    }
    
    public Object run(){
        // 请求上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        log.error("系统异常", ctx.getThrowable());
        // 返回服务器异常
        sendErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, StatusCode.SERVER_UNKNOWN_ERROR);
        return null;
    }

}
