package com.platform.system.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.platform.system.common.constant.RequestAttributeConst;
import com.platform.system.common.util.GuidGenerator;

/**
 * servlet上下文
 */
public final class ServletContextHolder {

    private ServletContextHolder() {}

    /**
     * 获取请求上下文
     * @return
     */
    public static final ServletRequestAttributes getCurrentServletRequestAttributes(){
        return (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    }

    /**
     * 当前请求
     * @return
     */
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes servletRequestAttributes = getCurrentServletRequestAttributes();
        if(null != servletRequestAttributes){
            return servletRequestAttributes.getRequest();
        }else{
            return null;
        }
    }

    /**
     * 当前响应
     * @return
     */
    public static HttpServletResponse getResponse(){
        ServletRequestAttributes servletRequestAttributes = getCurrentServletRequestAttributes();
        if(null != servletRequestAttributes){
            return servletRequestAttributes.getResponse();
        }else{
            return null;
        }
    }

    /**
     * 获取请求ID
     * @return
     */
    public static String fetchRequestId(){
        HttpServletRequest request = getRequest();
        if (null == request){
            return null;
        }
        String requestId = (String)request.getAttribute(RequestAttributeConst.REQUEST_ID);
        if(requestId == null){
            requestId = request.getHeader(RequestAttributeConst.REQUEST_ID);
            if(StringUtils.isEmpty(requestId)){
                requestId = GuidGenerator.generate32();
            }

            request.setAttribute(RequestAttributeConst.REQUEST_ID, requestId);
        }
        return requestId;
    }

}
