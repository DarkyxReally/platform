package com.platform.system.gate.zuul.filter.pre;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;

import com.netflix.zuul.context.RequestContext;
import com.platform.system.common.util.CommonUtil;
import com.platform.system.common.util.GuidGenerator;
import com.platform.system.common.util.WebUtil;
import com.platform.system.gate.zuul.properties.TraceProperties;
import com.platform.system.gate.zuul.properties.TraceProperties.Trace;

/** 请求朔源过滤器
 * 
 * */
@Slf4j
public class TracePreFilter extends BasePreFilter {

    private final TraceProperties properties;

    private final RouteLocator routeLocator;

    public TracePreFilter(int filterOrder, TraceProperties properties, RouteLocator routeLocator) {
        super(filterOrder);
        this.properties = properties;
        this.routeLocator = routeLocator;
    }

    /** 匹配路由
     * 
     * @param request
     * @return */
    private Route route(HttpServletRequest request) {
        String requestURI = getRequestUri(request);
        return routeLocator.getMatchingRoute(requestURI);
    }

    /** 根据路由匹配策略
     * 
     * @param route
     * @return */
    private Optional<TraceProperties.Policy> policy(Route route) {
        if(route != null) { return properties.getPolicy(route.getId()); }
        return Optional.empty();
    }

    @Override
    public boolean shouldFilter() {
        boolean enabled = properties.isEnabled();
        if(enabled) {
            // 开启功能
            RequestContext ctx = RequestContext.getCurrentContext();
            Optional<TraceProperties.Policy> policy = policy(route(ctx.getRequest()));
            if(policy.isPresent()) {
                // 路由策略存在, 使用路由指定的配置
                enabled = policy.get().isEnabled();
            }
        }
        return enabled;
    }

    /** 获取配置
     * 
     * @param policy
     * @return */
    private Trace getTrace(TraceProperties.Policy policy) {
        Trace trace = policy.getTrace();
        if(null == trace) {
            if(log.isDebugEnabled()) {
                log.debug("路由策略没有配置追踪数据, 将采用全局配置");
            }
            trace = properties.getTrace();
        }
        return trace;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Optional<TraceProperties.Policy> policy = policy(route(request));
        String traceId = GuidGenerator.generate32();
        MDC.put(properties.getTraceKey(), traceId);
        Trace trace;
        if(policy.isPresent()) {
            trace = getTrace(policy.get());
        }else{
            trace = properties.getTrace();
        }
        
        if (properties.isBehindProxy()){
            String ipAddress = WebUtil.getIpAddress(request);
            MDC.put(properties.getIpKey(), ipAddress);
        }else{
            MDC.put(properties.getIpKey(), request.getRemoteAddr());
        }
        
        if (null == trace){
            // 没有配置
            if(log.isDebugEnabled()) {
                log.debug("没有配置追踪数据");
            }
            return null;
        }
        
        // 本次请求的ID
        String id = trace.getId();
        if (StringUtils.isNotBlank(id)){
            List<String> str2List = CommonUtil.str2List(id, ",", true, true);
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(str2List)){
                for(String header : str2List){
                    ctx.addZuulRequestHeader(header, traceId);
                }
            }
        }
        String ip = trace.getIp();
        if (StringUtils.isNotBlank(ip)){
            String ipAddress = WebUtil.getIpAddress(request);
            List<String> str2List = CommonUtil.str2List(ip, ",", true, true);
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(str2List)){
                for(String header : str2List){
                    ctx.addZuulRequestHeader(header, ipAddress);
                }
            }
        }
        
        return null;
    }
}
