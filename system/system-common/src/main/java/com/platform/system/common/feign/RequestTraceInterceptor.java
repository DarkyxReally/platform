package com.platform.system.common.feign;


import com.platform.system.common.constant.RequestAttributeConst;
import com.platform.system.common.web.ServletContextHolder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * feign请求溯源拦截器
 * 在请求头增加本次请求的id
 * @version: 1.0
 */
public class RequestTraceInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate){
        requestTemplate.header(RequestAttributeConst.REQUEST_ID, ServletContextHolder.fetchRequestId());
    }
}