package com.platform.system.gate.zuul.filter.pre;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import com.netflix.zuul.context.RequestContext;
import com.platform.system.gate.zuul.FilterConstant;
import com.platform.system.gate.zuul.properties.RequestTimeStatsProperties;
import com.platform.system.gate.zuul.properties.RequestTimeStatsProperties.Policy;

/**
 * 请求时间统计过滤器
 * @version: 1.0
 */
public class RequestTimeLogPreFilter extends BasePreFilter {
    
    private final RouteLocator routeLocator;
    
    private final RequestTimeStatsProperties properties;

    public RequestTimeLogPreFilter(int filterOrder, RouteLocator routeLocator,
            RequestTimeStatsProperties properties) {
        super(filterOrder);
        this.routeLocator = routeLocator;
        this.properties = properties;
    }

    @Override
    public boolean shouldFilter(){
        boolean enabled = properties.isEnabled();
        if (enabled){
            // 没有配置
            if (properties.isAll()){
                // 开启了全局统计
                return true;
            }
            HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
            Route route = route(request);
            Optional<Policy> policy = policy(route);
            if (policy.isPresent()){
                // 有特定的配置
                enabled=  policy.get().isEnabled();
            }
        }
        return enabled;
    }
    
    private Route route(HttpServletRequest request) {
        String requestURI = getRequestUri(request);
        return routeLocator.getMatchingRoute(requestURI);
    }

    private Optional<Policy> policy(Route route) {
        if (route != null) {
            return properties.getPolicy(route.getId());
        }
        return Optional.empty();
    }

    @Override
    public Object run(){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.getRequest().setAttribute(FilterConstant.REQUEST_TIME_LOG_KEY, System.currentTimeMillis());
        return null;
    }
}
