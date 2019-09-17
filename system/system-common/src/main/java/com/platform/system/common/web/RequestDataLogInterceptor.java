package com.platform.system.common.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.platform.system.common.json.JsonUtil;
import com.platform.system.common.util.WebUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求数据记录拦截器
 * @version: 1.0
 */
@Slf4j
public class RequestDataLogInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception{
        // 请求的路径
        Enumeration<String> headerNames = request.getHeaderNames();
        List<Map<String, String>> headers = new ArrayList<Map<String, String>>();
        while(headerNames.hasMoreElements()){
            String name = (String)headerNames.nextElement();
            Map<String, String> header = new HashMap<String, String>();
            header.put(name, request.getHeader(name));
            headers.add(header);
        }
        if (!"/error".equals(request.getRequestURI())){
            log.info("请求uri:{}", request.getRequestURI());
            log.info("请求头:{}", JsonUtil.toJSONString(headers));
            String methodName = request.getMethod();
            if ("POST".equalsIgnoreCase(methodName) || "PUT".equalsIgnoreCase(methodName)){
                String reqBody = WebUtil.getRequestBodyString(request);
                log.info("请求body:{}", reqBody);
            }
            log.info("请求参数:{}", JsonUtil.toJSONString(request.getParameterMap()));
        }
        return true;
    }
}
