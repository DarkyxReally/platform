package com.platform.system.common.web;

import javax.servlet.http.HttpServletResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.ImmutableMap;

/**
 * 响应明细
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ResponseDetailsLogger {

    @JSONField(serialize= false, deserialize= false)
    private final HttpServletResponse response = ServletContextHolder.getResponse();

    @JSONField(name="headers")
    private ImmutableMap<String, Object> headers = fetchHttpHeaders(response);

    @JSONField(name="response_body")
    private Object responseBody;

    private ResponseDetailsLogger(Object responseBody) {
        this.responseBody = responseBody;
    }

    public static ResponseDetailsLogger with(Object responseBody) {
        return new ResponseDetailsLogger(responseBody);
    }

    private ImmutableMap<String, Object> fetchHttpHeaders(HttpServletResponse response) {
        final ImmutableMap.Builder<String, Object> headerBuilder = ImmutableMap.builder();
        for (String headerName : response.getHeaderNames()) {
            headerBuilder.put(headerName, response.getHeader(headerName));
        }
        return headerBuilder.build();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
