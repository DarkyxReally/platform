package com.platform.api.gate.filter.error.pre;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.exception.ZuulException;
import com.platform.api.gate.exception.InvalidTokenException;
import com.platform.system.common.enums.rest.AuthCode;
import com.platform.system.gate.zuul.FilterOrderConstant;
import com.platform.system.gate.zuul.filter.error.pre.BasePreErrorFilter;

/**
 * TOKEN无效情况处理
 * @version: 1.0
 */
@Component
public class InvalidTokenErrortFilter extends BasePreErrorFilter{

    public InvalidTokenErrortFilter(){
        super(FilterOrderConstant.VERSION_LIMIT_PRE_ERROR_ORDER+1);
    }
    
    public InvalidTokenErrortFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    protected boolean shouldFilter(ZuulException e){
        return e.getCause() instanceof InvalidTokenException;
    }

    public Object run(){
        sendErrorResponse(HttpStatus.OK, AuthCode.TOKEN_INVALID);
        return null;
    }
}
