package com.platform.system.gate.zuul.filter.pre;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.util.WebUtil;
import com.platform.system.gate.exception.IpLimitException;
import com.platform.system.gate.zuul.properties.IpLimitProperties;
import com.platform.system.gate.zuul.properties.IpLimitProperties.Policy;

/**
 * IP黑白名单限流过滤器
 * 
 * @version: 1.0
 */
@Slf4j
public class IpLimitPreFilter extends BasePreFilter {


    private final IpLimitProperties properties;
    private final RouteLocator routeLocator;

    public IpLimitPreFilter(int filterOrder, IpLimitProperties properties, RouteLocator routeLocator) {
        super(filterOrder);
        this.properties = properties;
        this.routeLocator = routeLocator;
    }

    @Override
    public boolean shouldFilter(){
        boolean isEnable = properties.isEnabled();
        if (isEnable){
            // 全局开关启用
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            Optional<IpLimitProperties.Policy> policy = policy(route(request));
            if (policy.isPresent()){
                // 路由指定开启与否
                isEnable = policy.get().isEnabled();
            }
        }
        return isEnable;
    }
    
    /**
     * 获取黑名单IP配置
     * @param policy
     * @return
     */
    private List<String> getBlackIps(Policy policy){
        List<String> blackIps = policy.getBlackIps();
        if (CollectionUtils.isEmpty(blackIps)){
            // 路由没有配置黑名单
            if(log.isDebugEnabled()) {
                log.debug("路由策略没有配置IP黑名单数据, 将采用全局配置");
            }
            // 采用全局的黑名单
            blackIps = properties.getBlackIps();
        }
        return blackIps;
    }
    
    /**
     * 获取白名单IP配置
     * @param policy
     * @return
     */
    private List<String> getWhiteIps(Policy policy){
        List<String> whiteIps = policy.getWhiteIps();
        if (CollectionUtils.isEmpty(whiteIps)){
            // 路由没有配置白名单
            if(log.isDebugEnabled()) {
                log.debug("路由策略没有配置IP白名单数据, 将采用全局配置");
            }
            // 采用全局的黑名单
            whiteIps = properties.getWhiteIps();
        }
        return whiteIps;
    }

    /**
     * 校验逻辑:
     * - 黑名单有数据, 并且IP在黑名单, 拒绝访问
     * - 白名单有数据, 且IP不在白名单里, 拒绝访问
     * - 其他: 允许访问
     * 
     * @return
     */
    @Override
    public Object run(){
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Route route = route(request);
        String ipAddress = WebUtil.getIpAddress(request);
        Optional<Policy> policy = policy(route);
        List<String> blackIps;
        List<String> whiteIps;
        if (policy.isPresent()){
            Policy routePolicy = policy.get();
            blackIps = getBlackIps(routePolicy);
            whiteIps = getWhiteIps(routePolicy);
        }else{
            blackIps = properties.getBlackIps();
            whiteIps = properties.getWhiteIps();
        }
        if (CollectionUtils.isNotEmpty(blackIps) && blackIps.contains(ipAddress)){
            if(log.isDebugEnabled()) {
                log.debug("IP地址:{},在黑名单IP中", ipAddress);
            }
            throw new ZuulRuntimeException(new ZuulException(new IpLimitException(), "非法访问", HttpStatus.FORBIDDEN.value(), "IP无效"));
        }
        
        if(CollectionUtils.isNotEmpty(whiteIps) && !whiteIps.contains(ipAddress)){
            if(log.isDebugEnabled()) {
                log.debug("IP地址:{},不在白名单IP中", ipAddress);
            }
            // 白名单有数据, 且IP不在白名单里, 拒绝访问
            throw new ZuulRuntimeException(new ZuulException(new IpLimitException(), "非法访问", HttpStatus.FORBIDDEN.value(), "IP无效"));
        }        
        return null;
    }

    /**
     * 匹配路由
     * @param request
     * @return
     */
    private Route route(HttpServletRequest request){
        String requestURI = getRequestUri(request);
        return routeLocator.getMatchingRoute(requestURI);
    }
    
    /** 根据路由匹配策略
     * 
     * @param route
     * @return */
    private Optional<IpLimitProperties.Policy> policy(Route route){
        if(route != null){
            return properties.getPolicy(route.getId());
        }
        return Optional.empty();
    }
}
