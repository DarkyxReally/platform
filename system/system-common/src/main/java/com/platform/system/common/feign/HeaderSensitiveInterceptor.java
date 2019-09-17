package com.platform.system.common.feign;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.platform.system.common.constant.CommonConstants;
import com.platform.system.common.util.CommonUtil;
import com.platform.system.common.web.ServletContextHolder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 请求头透传
 * 此处获取request对象需要改变隔离级别
 * hystrix.command.default.execution.isolation.strategy: SEMAPHORE 默认为THREAD
 * 否则获取不到request对象
 * @version: 1.0
 */
public class HeaderSensitiveInterceptor implements RequestInterceptor {

    @Autowired(required = false)
    private SensitiveHeaderConfig config;

    @Override
    public void apply(RequestTemplate requestTemplate){
        if(null == config || StringUtils.isBlank(config.getHeaders())){
            // 没有配置直接返回
            return;
        }
        // 逗号分隔
        Set<String> set = CommonUtil.str2Set(config.getHeaders(), CommonConstants.COMMA, true, true);
        if(CollectionUtils.isEmpty(set)){
            // 没设置透传
            return;
        }

        Map<String, List<String>> requestheaders = null;
        // 优先获取当请请求对象
        HttpServletRequest request = ServletContextHolder.getRequest();
        if(null != request){
            requestheaders = new HashMap<String, List<String>>();
            List<String> list = null;
            //过滤出需要透传的请求头
            for(String header : set){
                Enumeration<String> enumeration = request.getHeaders(header);
                if(!enumeration.hasMoreElements()){
                    continue;
                }
                list = new ArrayList<String>();
                while(enumeration.hasMoreElements()){
                    String string = (String)enumeration.nextElement();
                    list.add(string);
                }
                requestheaders.put(header, list);
            }
        }

        if(null != requestheaders){
            for(Entry<String, List<String>> entry : requestheaders.entrySet()){
                requestTemplate.header(entry.getKey(), entry.getValue().toArray(new String[]{}));
            }
        }
    }
}