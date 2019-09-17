package com.platform.system.gate.zuul.filter.pre;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.system.gate.exception.VersionLimitException;
import com.platform.system.gate.zuul.properties.VersionLimitProperties;
import com.platform.system.gate.zuul.properties.VersionLimitProperties.Policy;

/**
 * 版本校验过滤器
 * @version: 1.0
 */
@Slf4j
public class VersionLimitCheckPreFilter extends BasePreFilter {

    private final VersionLimitProperties properties;
    
    private final RouteLocator routeLocator;

    /**
     * 
     * @param filterOrder
     * @param properties
     * @param routeLocator
     */
    public VersionLimitCheckPreFilter(int filterOrder, VersionLimitProperties properties, RouteLocator routeLocator) {
        super(filterOrder);
        this.properties = properties;
        this.routeLocator = routeLocator;
    }

    @Override
    public boolean shouldFilter(){
        boolean enabled = properties.isEnabled();
        if (!properties.isEnabled()){
            if (log.isDebugEnabled()){
                log.debug("未开启版本号校验功能");
            }
            return false;
        }
        if (properties.isEnableAll()){
            // 所有接口都校验
            return true;
        }
        // 全局开关启用
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Route route = route(request);
        Optional<VersionLimitProperties.Policy> policy = policy(route);
        if (policy.isPresent()){
            // 路由指定开启与否
            enabled = policy.get().isEnabled();
            if (!enabled){
                if (log.isDebugEnabled()){
                    log.debug("路由:{},未开启版本号校验功能", route.getId());
                }
            }else{
                return true;
            }
        }
        return false;
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
    private Optional<VersionLimitProperties.Policy> policy(Route route){
        if(route != null){
            return properties.getPolicy(route.getId());
        }
        return Optional.empty();
    }
    
    /**
     * 获取版本号字段
     * @param policy
     * @return
     */
    private String getVersionField(Policy policy){
        String field = policy.getVersionField();
        if(StringUtils.isBlank(field)){
            if (log.isDebugEnabled()){
                log.debug("路由策略没有配置版本号字段, 将采用全局配置");
            }
            field = properties.getVersionField();
        }
        return field;
    }
    
    
    /**
     * 获取最低版本号
     * @param policy
     * @return
     */
    private String getMinVersion(Policy policy){
        String minVersion = policy.getMinVersion();
        if(StringUtils.isBlank(minVersion)){
            if (log.isDebugEnabled()){
                log.debug("路由策略没有配置最低版本号, 将采用全局配置");
            }
            minVersion = properties.getMinVersion();
        }
        return minVersion;
    }
    
    /**
     * 获取最高版本号
     * @param policy
     * @return
     */
    private String getMaxVersion(Policy policy){
        String maxVersion = policy.getMaxVersion();
        if(StringUtils.isBlank(maxVersion)){
            if (log.isDebugEnabled()){
                log.debug("路由策略没有配置最高版本号, 将采用全局配置");
            }
            maxVersion = properties.getMaxVersion();
        }
        return maxVersion;
    }

    @Override
    public Object run(){
        if (log.isDebugEnabled()){
            log.debug("开始进行版本号校验");
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Optional<Policy> policy = policy(route(request));
        String versionField;
        String minVersion;
        String maxVersion;
        if (policy.isPresent()){
            Policy routePolicy = policy.get();
            versionField = getVersionField(routePolicy);
            minVersion = getMinVersion(routePolicy);
            maxVersion = getMaxVersion(routePolicy);
        }else{
            versionField = properties.getVersionField();
            minVersion = properties.getMinVersion();
            maxVersion = properties.getMaxVersion();
        }
        if (StringUtils.isBlank(versionField)){
            if(log.isDebugEnabled()) {
                log.debug("没有配置版本号字段");
            }
            return null;
        }
        String version = request.getHeader(versionField);
        if(StringUtils.isBlank(version)){
            if(log.isDebugEnabled()) {
                log.debug("版本号不存在");
            }
            throw new ZuulRuntimeException(new ZuulException(new VersionLimitException(), "非法访问", HttpStatus.FORBIDDEN.value(), "缺乏请求信息"));
        }
        
        if (StringUtils.isNotBlank(minVersion)){
//            if (!AppVersionUtils.isVersionAllow(version, minVersion)){
//                // 版本号过低
//                if(log.isDebugEnabled()) {
//                    log.debug("版本号:{},过低", version);
//                }
//                throw new ZuulRuntimeException(new ZuulException(new VersionLimitException(), "版本号不正确", HttpStatus.FORBIDDEN.value(), "版本号过低"));
//            }
        }
        if (StringUtils.isNotBlank(maxVersion)){
//            if (!AppVersionUtils.isVersionAllow(maxVersion, version)){
//                // 版本号过高
//                if(log.isDebugEnabled()) {
//                    log.debug("版本号:{},过高", version);
//                }
//                throw new ZuulRuntimeException(new ZuulException(new VersionLimitException(), "版本号不正确", HttpStatus.FORBIDDEN.value(), "版本号过高"));
//            }
        }
        
        return null;
    }
}
