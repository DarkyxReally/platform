package com.platform.system.gate.zuul.filter.pre;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.system.gate.exception.HeaderValueErrorException;
import com.platform.system.gate.zuul.properties.HeaderValueCheckProperties;
import com.platform.system.gate.zuul.properties.HeaderValueCheckProperties.Policy;

/** 请求头值校验过滤器
 * 
 * @version: 1.0
 * */
@Slf4j
public class HeaderValueCheckPreFilter extends BasePreFilter {

    private final HeaderValueCheckProperties properties;

    private final RouteLocator routeLocator;

    public HeaderValueCheckPreFilter(int filterOrder, HeaderValueCheckProperties properties, RouteLocator routeLocator) {
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
            Route route = route(request);
            Optional<HeaderValueCheckProperties.Policy> policy = policy(route);
            if(policy.isPresent()) {
                // 路由策略存在, 使用路由指定的配置
                enabled = policy.get().isEnabled();
            }
        }

        return enabled;
    }

    /** 根据URI匹配路由
     * 
     * @param requestUri
     * @return */
    private Route route(HttpServletRequest request) {
        String requestUri = getRequestUri(request);
        return routeLocator.getMatchingRoute(requestUri);
    }

    /** 根据路由匹配策略
     * 
     * @param route
     * @return */
    private Optional<HeaderValueCheckProperties.Policy> policy(Route route) {
        if(route != null) { return properties.getPolicy(route.getId()); }
        return Optional.empty();
    }

    /** 获取要校验的请求头数据
     * 
     * @param policy
     * @return */
    private Map<String, String> getHeaderValue(Policy policy) {
        Map<String, String> headValues = policy.getHeaderValues();
        if(CollectionUtils.isEmpty(headValues)) {
            if(log.isDebugEnabled()) {
                log.debug("路由策略没有配置要校验的请求头数据, 将采用全局配置");
            }
            headValues = properties.getHeaderValues();
            if(CollectionUtils.isEmpty(headValues)) {
                if(log.isDebugEnabled()) {
                    log.debug("全局配置没有设置要校验的请求头数据");
                }
            }
        }
        return headValues;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Route route = route(request);
        Optional<HeaderValueCheckProperties.Policy> policy = policy(route);
        // 请求头键值对
        Map<String, String> headValues;
        if(policy.isPresent()) {
            // 路由策略存在, 使用路由指定的配置
            Policy routePolicy = policy.get();
            // 路由没有开启根据版本号进行区分, 采用路由配置的请求头数据
            headValues = getHeaderValue(routePolicy);
        } else {
            // 没有根据版本号进行区分, 采用默认的请求头数据
            headValues = properties.getHeaderValues();
        }

        if(CollectionUtils.isEmpty(headValues)) {
            // / 没有要校验的请求头数据
            if(log.isDebugEnabled()) {
                log.debug("请求头数据不存在");
            }
            return null;
        }

        for(Entry<String, String> entry : headValues.entrySet()) {
            if(!entry.getValue().equals(request.getHeader(entry.getKey()))) {
                if(log.isDebugEnabled()) {
                    log.debug("请求头:{},值不匹配", entry.getKey());
                }
                throw new ZuulRuntimeException(new ZuulException(new HeaderValueErrorException(),"非法访问", HttpStatus.FORBIDDEN.value(), "数据不匹配"));
            }
        }
        return null;
    }

}
