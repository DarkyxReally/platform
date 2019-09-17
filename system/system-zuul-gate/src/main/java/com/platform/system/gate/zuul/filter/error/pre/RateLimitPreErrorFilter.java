package com.platform.system.gate.zuul.filter.error.pre;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.gate.zuul.FilterConstant;

/**
 * PRE异常处理Filter
 * 处理请求频率达到限制的情况
 */
@Slf4j
public class RateLimitPreErrorFilter extends BasePreErrorFilter {

    public RateLimitPreErrorFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    protected boolean shouldFilter(ZuulException cause) {
        RequestContext ctx = RequestContext.getCurrentContext();
        return "true".equals(ctx.get(FilterConstant.RATE_LIMIT_EXCEEDED));
    }

    public Object run(){
        if (log.isDebugEnabled()){
            log.debug("请求达到频率限制, 返回请勿频繁操作");
        }
        sendErrorResponse(HttpStatus.TOO_MANY_REQUESTS, StatusCode.RATE_LIMIT_REQUEST, "请勿频繁操作");
        RequestContext ctx = RequestContext.getCurrentContext();
        // 清除标志
        ctx.remove(FilterConstant.RATE_LIMIT_EXCEEDED);
        return null;
    }
}
