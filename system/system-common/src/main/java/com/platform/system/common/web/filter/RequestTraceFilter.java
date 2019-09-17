package com.platform.system.common.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import com.platform.system.common.constant.RequestAttributeConst;
import com.platform.system.common.trace.TraceProperties;
import com.platform.system.common.util.GuidGenerator;
import com.platform.system.common.util.WebUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求溯源过滤器
 * @version: 1.0
 */
@Slf4j
public class RequestTraceFilter extends OncePerRequestFilter {

    /**
     * 本次请求的追踪溯源ID
     */
    public static final String REQUEST_TRACE_ID = TraceProperties.TRACE_ID;
    /**
     * 请求IP
     */
    public static final String HOST_IP = "HOST_IP";

    /**
     * 默认实现中填充了UUID，主机IP和主机地址
     * @param request
     */
    protected void insertIntoMDC(HttpServletRequest request){
        if (StringUtils.isBlank(request.getHeader(RequestAttributeConst.REQUEST_ID))){
            MDC.put(REQUEST_TRACE_ID, GuidGenerator.generate32());
        }
        else{
            MDC.put(REQUEST_TRACE_ID, request.getHeader(RequestAttributeConst.REQUEST_ID));
        }
        
        if (StringUtils.isBlank(request.getHeader(RequestAttributeConst.USER_IP))){
            MDC.put(HOST_IP, WebUtil.getIpAddress(request));
        }
        else{
            MDC.put(HOST_IP, request.getHeader(RequestAttributeConst.USER_IP));
        }
    }

    //移除
    protected void clearMDC(){
        MDC.remove(REQUEST_TRACE_ID);
        MDC.remove(HOST_IP);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException,IOException{
        
        try{
            insertIntoMDC(request);
//            if (log.isDebugEnabled()){
                log.info("============{}请求URI:{}", request.getMethod(), request.getRequestURI());
//            }
            super.doFilter(request, response, filterChain);
        }finally{
            clearMDC();
        }
    }

}