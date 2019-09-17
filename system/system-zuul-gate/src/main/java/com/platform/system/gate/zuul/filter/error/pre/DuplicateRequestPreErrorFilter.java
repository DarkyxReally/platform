package com.platform.system.gate.zuul.filter.error.pre;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.gate.exception.DuplicateRequestException;

/** 重复请求的error filter处理
 * 
 * @version: 1.0
 * */
@Slf4j
public class DuplicateRequestPreErrorFilter extends BasePreErrorFilter {

    public DuplicateRequestPreErrorFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    protected boolean shouldFilter(ZuulException cause) {
        return cause.getCause() instanceof DuplicateRequestException;
    }

    @Override
    public Object run() {
        if(log.isDebugEnabled()) {
            log.debug("返回重复请求");
        }
        // 返回非法请求
        sendErrorResponse(HttpStatus.OK, StatusCode.DUPLICATE_REQUEST, "重复请求");
        return null;
    }

}
