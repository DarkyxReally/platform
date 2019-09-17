package com.platform.system.gate.zuul.filter.error.pre;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.VersionCode;
import com.platform.system.gate.exception.VersionLimitException;

/** 版本号限制处理
 * 
 * @version: 1.0 
 * */
@Slf4j
public class VersionLimitPreErrorFilter extends BasePreErrorFilter {

    public VersionLimitPreErrorFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    public boolean shouldFilter(ZuulException e) {
        return e.getCause() instanceof VersionLimitException;
    }

    public Object run() {
        if(log.isDebugEnabled()) {
            log.debug("返回版本号无效");
        }
        sendErrorResponse(HttpStatus.UNAUTHORIZED, VersionCode.ERROR_VERSION, "版本号无效");
        return null;
    }
}
