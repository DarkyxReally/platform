package com.platform.system.gate.zuul.filter.error.pre;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.gate.exception.HeaderValueErrorException;

/**
 * 请求头校验限制的error filter处理
 * @version: 1.0
 */
@Slf4j
public class HeaderValueCheckPreErrorFilter extends BasePreErrorFilter {

	public HeaderValueCheckPreErrorFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    protected boolean shouldFilter(ZuulException cause) {
        return cause.getCause() instanceof HeaderValueErrorException;
    }

	@Override
	public Object run() {
	    if (log.isDebugEnabled()){
            log.debug("返回头信息无效");
        }
		sendErrorResponse(HttpStatus.OK, StatusCode.INVALID_REQUEST, "头信息无效");
		return null;
	}

}
