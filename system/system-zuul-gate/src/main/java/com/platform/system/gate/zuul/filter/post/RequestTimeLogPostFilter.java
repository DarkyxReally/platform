package com.platform.system.gate.zuul.filter.post;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class RequestTimeLogPostFilter extends BasePostFilter {
    
    
    private final RouteLocator routeLocator;
    
    private final RequestTimeStatsProperties properties;

    public RequestTimeLogPostFilter(int filterOrder, RouteLocator routeLocator,
            RequestTimeStatsProperties properties) {
        super(filterOrder);
        this.routeLocator = routeLocator;
        this.properties = properties;
    }
    
    @Override
    public boolean shouldFilter(){
        if (!properties.isEnabled()){
            return false;
        }  
        Route route = route();
        Optional<Policy> policy = policy(route);
        boolean isPresent = policy.isPresent();
        if (!isPresent){
            // 没有配置
            if (properties.isAll()){
                // 开启了全局统计
                return true;
            }
        }else{
            // 有特定的配置
            if (!policy.get().isEnabled()){
                // 没有启用
                return false;
            }
        }
        
        return true;
    }

    private Route route() {
        String requestURI = getRequestUri(RequestContext.getCurrentContext().getRequest());
        return routeLocator.getMatchingRoute(requestURI);
    }

    private Optional<Policy> policy(final Route route) {
        if (route != null) {
            return properties.getPolicy(route.getId());
        }
        return Optional.empty();
    }


    @Override
    public Object run(){
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        Object reqTime = request.getAttribute(FilterConstant.REQUEST_TIME_LOG_KEY);
        if(reqTime != null){
            try {
                String requestUri = getRequestUri(request);
                long useTime = System.currentTimeMillis() - (long)reqTime;
                Route route = route();
                Optional<Policy> policy = policy(route);
                int slowRequestMillisecond = properties.getSlowRequestMillisecond();
                if (policy.isPresent()){
                    slowRequestMillisecond = policy.get().getSlowRequestMillisecond();
                }
                
                if(useTime > slowRequestMillisecond){
                    log.warn("slow request, use time:{}ms, url:{}", useTime, requestUri);
                } else {
                    if (log.isDebugEnabled()){
                        log.debug("request use time:{}ms, url:{}", useTime, requestUri);
                    }
                }
            }catch (Throwable e){
                log.error(e.getLocalizedMessage(), e);
            }finally {
                request.removeAttribute(FilterConstant.REQUEST_TIME_LOG_KEY);
            }
        }
        return null;
    }
}
