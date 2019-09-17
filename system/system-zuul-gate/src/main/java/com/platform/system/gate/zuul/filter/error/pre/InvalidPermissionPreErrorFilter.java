package com.platform.system.gate.zuul.filter.error.pre;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.gate.exception.InvalidPermissionException;

/**
 * 没有权限引发的error filter处理
 * @version: 1.0
 */
@Slf4j
public class InvalidPermissionPreErrorFilter extends BasePreErrorFilter {

    public InvalidPermissionPreErrorFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    protected boolean shouldFilter(ZuulException e) {
        return e.getCause() instanceof InvalidPermissionException;
    }

    @Override
    public Object run() {
        if(log.isDebugEnabled()) {
            log.debug("返回没有权限访问");
        }
        sendErrorResponse(HttpStatus.OK, StatusCode.INVALID_PERMISSION_MISS, "没有权限访问");
        return null;
    }

}
