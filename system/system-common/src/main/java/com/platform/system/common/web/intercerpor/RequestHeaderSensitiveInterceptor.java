package com.platform.system.common.web.intercerpor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.platform.system.common.constant.CommonConstants;
import com.platform.system.common.feign.SensitiveHeaderConfig;
import com.platform.system.common.util.CommonUtil;


/**
 * 透传请求头
 * @version: 1.0
 */
public class RequestHeaderSensitiveInterceptor extends HandlerInterceptorAdapter {

    @Autowired(required = false)
    private SensitiveHeaderConfig config;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        if(null == config || StringUtils.isBlank(config.getHeaders())){
            // 没有配置直接返回
            return true;
        }
        // 逗号分隔
        Set<String> headers = CommonUtil.str2Set(config.getHeaders(), CommonConstants.COMMA, true, true);
        if(CollectionUtils.isEmpty(headers)){
            // 没设置透传
            return true;
        }
        Map<String, List<String>> headersMap = new HashMap<String, List<String>>();
        List<String> list = null;
        for(String header : headers){
            Enumeration<String> enumeration = request.getHeaders(header);
            if(!enumeration.hasMoreElements()){
                continue;
            }
            list = new ArrayList<String>();
            while(enumeration.hasMoreElements()){
                String string = (String)enumeration.nextElement();
                list.add(string);
            }
            headersMap.put(header, list);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception{
        super.afterCompletion(request, response, handler, ex);
    }
}
