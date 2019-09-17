package com.platform.system.gate.zuul.filter.pre;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;

import com.google.common.collect.Lists;
import com.netflix.zuul.context.RequestContext;
import com.platform.system.common.json.JsonUtil;
import com.platform.system.common.util.WebUtil;
import com.platform.system.gate.zuul.properties.RequestLogProperties;
import com.platform.system.gate.zuul.properties.RequestLogProperties.Level;

/** 请求朔源过滤器
 * 
 * @version: 1.0.
 * */
@Slf4j
public class RequestLogPreFilter extends BasePreFilter {

    private final RequestLogProperties properties;
    private final RouteLocator routeLocator;

    public RequestLogPreFilter(int filterOrder, RequestLogProperties properties, RouteLocator routeLocator) {
        super(filterOrder);
        this.properties = properties;
        this.routeLocator = routeLocator;
    }

    @Override
    public boolean shouldFilter() {
        boolean enabled = properties.isEnabled();
        if(enabled) {
            // 开启校验请求头值功能
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            String requestUri = getRequestUri(request);
            Route route = route(requestUri);
            Optional<RequestLogProperties.Policy> policy = policy(route);
            if(policy.isPresent()) {
                // 路由指定开启与否
                enabled = policy.get().isEnabled();
            }
        }

        return enabled;
    }
    
    /**
     * 根据路由匹配策略
     * @param route
     * @return
     */
    private Optional<RequestLogProperties.Policy> policy(Route route) {
        if (route != null) {
            return properties.getPolicy(route.getId());
        }
        return Optional.empty();
    }

    /** 根据URI匹配路由
     * 
     * @param requestUri
     * @return */
    private Route route(String requestUri) {
        return routeLocator.getMatchingRoute(requestUri);
    }
    
    
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Level logLevel;
        List<String> ingoreHeaders;
        String requestUri = getRequestUri(request);
        Route route = route(requestUri);
        Optional<RequestLogProperties.Policy> policy = policy(route);
        if(policy.isPresent()) {
            // 路由策略存在, 使用路由指定的配置
            RequestLogProperties.Policy routePolicy = policy.get();
            logLevel = routePolicy.getLogLevel();
            ingoreHeaders = routePolicy.getIngoreHeaders();
        }else{
            // 采用全局配置
            logLevel = properties.getLogLevel();
            ingoreHeaders = properties.getIngoreHeaders();
        }
        if (null == logLevel){
            logLevel = Level.DEBUG;
        }
        // 记录请求地址
        logMessage(logLevel, "URL:{}, method:{}", requestUri, request.getMethod());
        // 记录请求头
        logRequestHeader(request, ingoreHeaders, logLevel);
        // 记录请求参数
        logRequestParams(request, logLevel);
        // 记录请求体
        logRequestBody(request, logLevel);
        return null;
    }
    
    
    /**
     * 记录消息
     * @param level 级别
     * @param format 格式
     * @param arguments 参数
     */
    private void logMessage(Level level, String format, Object... arguments){
        switch(level){
            case DEBUG:
                if (log.isDebugEnabled()){
                    log.debug(format, arguments);
                }
                break;
            case INFO:
                if (log.isInfoEnabled()){
                    log.info(format, arguments);
                }
                break;
            case WARN:
                if (log.isWarnEnabled()){
                    log.warn(format, arguments);
                }
                break;
            case ERROR:
                if (log.isErrorEnabled()){
                    log.error(format, arguments);
                }
                break;

            default:
                log.debug(format, arguments);
                break;
        }
    }
    
    /**
     * 记录请求头
     * @param request 请求对象
     * @param ignoreHeaders 忽略的请求头
     * @param logLevel 日志级别
     */
    private void logRequestHeader(HttpServletRequest request, List<String> ignoreHeaders, Level logLevel){
        if (null == ignoreHeaders){
            ignoreHeaders = Lists.newArrayList();
        }
        Enumeration<String> headEnumeration = request.getHeaderNames();
        Map<String, List<String>> headerValues = new HashMap<String, List<String>>();
        while(headEnumeration.hasMoreElements()){
            String headerName = headEnumeration.nextElement();
            List<String> list = headerValues.get(headerName);
            if (null == list){
                list = new LinkedList<String>();
                headerValues.put(headerName, list);
            }
            
            Enumeration<String> headers = request.getHeaders(headerName);
            while(headers.hasMoreElements()){
                String value = headers.nextElement();
                if (ignoreHeaders.contains(headerName)){
                    // 忽略的请求头用*代替
                    value = "*";
                }
                list.add(value);
            }
        }
        String headerInfo = JsonUtil.toJSONString(headerValues);
        logMessage(logLevel, "headers:{}", headerInfo);
    }
    
    /**
     * 记录请求参数
     * @param request 请求对象
     * @param logLevel 日志级别
     */
    private void logRequestParams(HttpServletRequest request, Level logLevel){
        Map<String, String[]> map = request.getParameterMap();
        String params = JsonUtil.toJSONString(map);
        String template = "params:{}";
        switch(logLevel){
            case DEBUG:
                if (log.isDebugEnabled()){
                    log.debug(template, params);
                }
                break;
            case INFO:
                if (log.isInfoEnabled()){
                    log.info(template, params);
                }
                break;
            case WARN:
                if (log.isWarnEnabled()){
                    log.warn(template, params);
                }
                break;
            case ERROR:
                if (log.isErrorEnabled()){
                    log.error(template, params);
                }
                break;

            default:
                log.debug(template, params);
                break;
        }
    }
    
    /**
     * 记录请求体
     * @param request 请求对象
     * @param logLevel 日志级别
     */
    private void logRequestBody(HttpServletRequest request, Level logLevel){
        String method = request.getMethod();
        if (!"POST".equalsIgnoreCase(method) && !"PUT".equalsIgnoreCase(method)){
            // 仅限POST请求和PUT请求
            return;
        }
        String contentType = request.getContentType();
        if(StringUtils.isNotBlank(contentType)&&contentType.startsWith("multipart/form-data")){
            // 文件上传类型不记录
            return;
        }
        
        try{
            String body = WebUtil.getRequestBodyString(request);
            String template = "body:{}";
            switch(logLevel){
                case DEBUG:
                    if (log.isDebugEnabled()){
                        log.debug(template, body);
                    }
                    break;
                case INFO:
                    if (log.isInfoEnabled()){
                        log.info(template, body);
                    }
                    break;
                case WARN:
                    if (log.isWarnEnabled()){
                        log.warn(template, body);
                    }
                    break;
                case ERROR:
                    if (log.isErrorEnabled()){
                        log.error(template, body);
                    }
                    break;

                default:
                    log.debug(template, body);
                    break;
            }
        }catch(Exception e){
            log.error("读取请求body异常:"+e.getMessage(), e);
        }
    }

}
