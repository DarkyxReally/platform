package com.platform.system.gate.zuul.filter.error.post;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.gate.exception.ResponseSignException;
/**
 * 响应数据签名出现异常错误处理
 * @version: 1.0
 */
@Slf4j
public class ResponseDataSignPostErrorFilter extends BasePostErrorFilter {

    public ResponseDataSignPostErrorFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    protected boolean shouldFilter(ZuulException e) {
        return e.getCause() instanceof ResponseSignException;
    }

    @Override
    public Object run() {
        if (log.isDebugEnabled()){
            log.debug("响应数据签名异常, 返回服务器异常");
        }
        sendErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, StatusCode.SERVER_UNKNOWN_ERROR);
        return null;
    }

}
