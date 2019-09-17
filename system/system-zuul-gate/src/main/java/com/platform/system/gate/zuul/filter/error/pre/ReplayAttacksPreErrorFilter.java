package com.platform.system.gate.zuul.filter.error.pre;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.gate.exception.ReplayAttackstException;

/**
 * 重放攻击的error filter处理
 * @version: 1.0
 */
@Slf4j
public class ReplayAttacksPreErrorFilter extends BasePreErrorFilter {

    public ReplayAttacksPreErrorFilter(int filterOrder) {
        super(filterOrder);
    }

    @Override
    protected boolean shouldFilter(ZuulException e) {
        return e.getCause() instanceof ReplayAttackstException;
    }

    @Override
    public Object run() {
        if (log.isDebugEnabled()){
            log.debug("重放攻击, 返回：请求报文无效");
        }
        // 返回非法请求
        sendResponse(HttpStatus.OK, StatusCode.DUPLICATE_REQUEST, "请求报文无效");
        return null;
    }

}
