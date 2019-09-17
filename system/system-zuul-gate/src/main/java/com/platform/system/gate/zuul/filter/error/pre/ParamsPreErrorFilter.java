package com.platform.system.gate.zuul.filter.error.pre;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.gate.exception.ParamsErrorException;

/** 缺乏请求参数的error filter处理
 * 
 * @version:1.0
 * */
@Slf4j
public class ParamsPreErrorFilter extends BasePreErrorFilter {

    public ParamsPreErrorFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    protected boolean shouldFilter(ZuulException e) {
        return e.getCause() instanceof ParamsErrorException;
    }

    @Override
    public Object run() {
        if(log.isDebugEnabled()) {
            log.debug("返回参数格式有误");
        }
        sendErrorResponse(HttpStatus.OK, StatusCode.INVALID_MODEL_FIELDS, "参数格式有误");
        return null;
    }

}
