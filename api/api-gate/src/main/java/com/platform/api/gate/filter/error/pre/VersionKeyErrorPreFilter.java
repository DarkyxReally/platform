package com.platform.api.gate.filter.error.pre;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.exception.ZuulException;
import com.platform.api.gate.exception.InvalidVersionKeyException;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.gate.zuul.FilterOrderConstant;
import com.platform.system.gate.zuul.filter.error.pre.BasePreErrorFilter;

/**
 * 版本KEY无效情况处理
 * @version: 2.6.1
 */
@Component
public class VersionKeyErrorPreFilter extends BasePreErrorFilter{

    public VersionKeyErrorPreFilter(){
        super(FilterOrderConstant.VERSION_LIMIT_PRE_ERROR_ORDER+1);
    }
    
    public VersionKeyErrorPreFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    protected boolean shouldFilter(ZuulException e){
        return e.getCause() instanceof InvalidVersionKeyException;
    }

    public Object run(){
        sendErrorResponse(HttpStatus.FORBIDDEN, StatusCode.INVALID_REQUEST);
        return null;
    }
}
