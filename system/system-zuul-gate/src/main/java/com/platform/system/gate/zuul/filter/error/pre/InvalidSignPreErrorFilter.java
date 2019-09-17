package com.platform.system.gate.zuul.filter.error.pre;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.gate.exception.InvalidSignException;

/** 签名无效的error filter处理
 * 
 * @version: 1.0 
 * */
@Slf4j
public class InvalidSignPreErrorFilter extends BasePreErrorFilter {

    public InvalidSignPreErrorFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    protected boolean shouldFilter(ZuulException cause) {
        return cause.getCause() instanceof InvalidSignException;
    }

    @Override
    public Object run() {
        if(log.isDebugEnabled()) {
            log.debug("签名验证不通过");
        }
        sendErrorResponse(HttpStatus.OK, StatusCode.INVALID_SIGN, "签名无效");
        return null;
    }

}
