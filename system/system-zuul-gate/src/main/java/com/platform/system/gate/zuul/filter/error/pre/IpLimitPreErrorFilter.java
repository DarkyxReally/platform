package com.platform.system.gate.zuul.filter.error.pre;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.gate.exception.IpLimitException;

/**
 * IP限制的error filter处理
 * @version: 1.0
 */
@Slf4j
public class IpLimitPreErrorFilter extends BasePreErrorFilter {

	public IpLimitPreErrorFilter(int filterOrder) {
        super(filterOrder);
    }
	
    @Override
    protected boolean shouldFilter(ZuulException cause) {
        return cause.getCause() instanceof IpLimitException;
    }

	@Override
	public Object run() {
	    if (log.isDebugEnabled()){
	        log.debug("返回IP无效");
	    }
	    sendErrorResponse(HttpStatus.OK, StatusCode.INVALID_REQUEST, "IP无效");
		return null;
	}
}
