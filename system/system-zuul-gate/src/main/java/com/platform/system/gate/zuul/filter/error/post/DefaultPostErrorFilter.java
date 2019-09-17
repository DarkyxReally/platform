package com.platform.system.gate.zuul.filter.error.post;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.StatusCode;

/** 默认后置异常处理
 * 
 * @version: 1.0
 * */
@Slf4j
public class DefaultPostErrorFilter extends BasePostErrorFilter {

    public DefaultPostErrorFilter(int filterOrder) {
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
        log.error("后置处理出现异常, 返回服务器异常", throwable);
        sendErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, StatusCode.SERVER_UNKNOWN_ERROR);
        return null;
    }

}
