package com.platform.system.common.web;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.ImmutableMap;
import com.platform.system.common.constant.RequestAttributeConst;

import lombok.Getter;
import lombok.Setter;

/**
 * 请求明细记录
 */
@Getter
@Setter
public class RequestDetailsLogger {

    @JSONField(serialize=false, deserialize = false)
    private final HttpServletRequest request = ServletContextHolder.getRequest();

    @JSONField(name="request_id")
    private String requestId = ServletContextHolder.fetchRequestId();

    @JSONField(name="url")
    private String url = request.getRequestURL().toString();

    @JSONField(name="method")
    private String method = request.getMethod();

    @JSONField(name="params_map")
    private ImmutableMap<String, Object> paramsMap = fetParamsMap(request);

    @JSONField(name="headers")
    private ImmutableMap<String, Object> headers = fetchHttpHeaders(request);

    @JSONField(name="api_desc")
    private String apiDesc;

    @JSONField(name="request_body")
    private String requestBody = (String) ServletContextHolder.getRequest().getAttribute(RequestAttributeConst.REQUEST_BODY_KEY);

    @JSONField(name="request_time")
    private long requestTime = System.nanoTime();

    @JSONField(name="response_time")
    private long responseTime;

    @JSONField(name="character_encoding")
    private String characterEncoding = request.getCharacterEncoding();

    @JSONField(name="content_length")
    private long contentLength = request.getContentLengthLong();

    @JSONField(name="remote_host")
    private String remoteHost = request.getRemoteHost();

    @JSONField(name="remote_port")
    private int remotePort = request.getRemotePort();

    private ImmutableMap<String, Object> fetParamsMap(HttpServletRequest request) {
        final Map<String, String[]> parameterMap = request.getParameterMap();
        final ImmutableMap.Builder<String, Object> singleValueParams = ImmutableMap.builder();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            singleValueParams.put(entry.getKey(), entry.getValue()[0]);
        }
        return singleValueParams.build();
    }

    private ImmutableMap<String, Object> fetchHttpHeaders(HttpServletRequest request) {
        final ImmutableMap.Builder<String, Object> headerBuilder = ImmutableMap.builder();
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            headerBuilder.put(headerName, request.getHeader(headerName));
        }
        return headerBuilder.build();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
