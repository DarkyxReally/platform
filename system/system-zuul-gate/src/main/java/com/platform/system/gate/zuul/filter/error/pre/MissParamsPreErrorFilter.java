package com.platform.system.gate.zuul.filter.error.pre;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.gate.exception.ParamsMissException;

/**
 * 缺乏请求参数的error filter处理
 * @version: 1.0
 */
@Slf4j
public class MissParamsPreErrorFilter extends BasePreErrorFilter {

	public MissParamsPreErrorFilter(int filterOrder) {
        super(filterOrder);
    }
	
	@Override
	protected boolean shouldFilter(ZuulException e) {
	    return e.getCause() instanceof ParamsMissException;
	}

	@Override
	public Object run() {
	    if (log.isDebugEnabled()){
	        log.debug("返回缺少必要的参数");
	    }
		sendErrorResponse(HttpStatus.OK, StatusCode.INVALID_MODEL_FIELDS, "缺少必要的参数");
		return null;
	}
}
