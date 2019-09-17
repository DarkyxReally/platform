package com.platform.system.gate.zuul.filter.post;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.util.RsaKeyHelper;
import com.platform.system.gate.exception.ResponseEncryptException;
import com.platform.system.gate.zuul.properties.ResponseDataEncryptProperties;
import com.platform.system.gate.zuul.properties.ResponseDataEncryptProperties.Policy;

/**
 * 响应数据加密拦截器
 * @version: 1.0
 */
@Slf4j
public class ResponseDataEncryptPostFilter extends BasePostFilter {
    
    private static final Charset UTF8 = Charset.forName("UTF-8");
    
    private final RouteLocator routeLocator;
    
    private final ResponseDataEncryptProperties properties;

    public ResponseDataEncryptPostFilter(int filterOrder, RouteLocator routeLocator,
            ResponseDataEncryptProperties properties) {
        super(filterOrder);
        this.routeLocator = routeLocator;
        this.properties = properties;
    }
    
    @Override
    public boolean shouldFilter(){
        if(!properties.isEnabled()) {
            if (log.isDebugEnabled()){
                log.debug("未开启响应报文加密功能");
            }
            return false;
        }
        if (properties.isEnableAll()){
            // 强制开启所有
            return true;
        }
        // 功能开启
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        Route route = route(request);
        Optional<ResponseDataEncryptProperties.Policy> policy = policy(route);
        if(policy.isPresent()) {
            boolean enabled = policy.get().isEnabled();
            if (!enabled){
                if (log.isDebugEnabled()){
                    log.debug("路由:{},未开启响应报文加密功能", route.getId());
                }
                return false;
            }else{
                return true;
            }
        }else{
            // 没有特定路由指定, 默认根据版本号来
            String versionField = properties.getVersionField();
            String minVersion = properties.getMinVersion();
            if (StringUtils.isNotBlank(versionField) && StringUtils.isNotBlank(minVersion)){
//                String version = request.getHeader(versionField);
//                if (StringUtils.isBlank(version)){
//                    if (log.isDebugEnabled()){
//                        log.debug("版本为空,不启用响应报文签名");
//                    }
//                    return false;
//                }
//                if (!AppVersionUtils.isVersionAllow(version, minVersion)){
//                    // 版本号低于启用的版本号
//                    if (log.isDebugEnabled()){
//                        log.debug("当前版本号:{}, 低于响应报文签名的最低版本号", route.getId());
//                    }
//                    return false;
//                }
                return true;
            }else{
                if (log.isDebugEnabled()){
                    log.debug("版本号字段:[{}]或最低版本号:[{}]未配置, 默认不开启响应报文签名", versionField, minVersion);
                }
                return false;
            }
        }
    }

    private Route route(HttpServletRequest request) {
        String requestURI = getRequestUri(request);
        return routeLocator.getMatchingRoute(requestURI);
    }

    private Optional<Policy> policy(final Route route) {
        if (route != null) {
            return properties.getPolicy(route.getId());
        }
        return Optional.empty();
    }
    
    /** 获取解密密钥
     * 
     * @param policy
     * @return */
    private String getPriKey(Policy policy) {
        String key = policy.getPriKey();
        if(StringUtils.isBlank(key)) {
            if(log.isDebugEnabled()) {
                log.debug("路由策略没有配置解密密钥, 将采用全局配置");
            }
            key = properties.getPriKey();
            if(StringUtils.isEmpty(key)) {
                if(log.isDebugEnabled()) {
                    log.debug("全局配置没有设置解密密钥");
                }
            }
        }
        return key;
    }

    @Override
    public Object run(){
        if (log.isDebugEnabled()){
            log.debug("开始进行响应报文加密处理");
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Route route = route(request);
        Optional<Policy> policy = policy(route);
        String priKey;
        if (policy.isPresent()){
            priKey = getPriKey(policy.get());
        }else{
            priKey = properties.getPriKey();
        }
        String responseBody = ctx.getResponseBody();
        String body;
        if(StringUtils.isNotEmpty(responseBody)){
            // 没有经过control 直接从Filter中抛出异常时的情况
            body = responseBody;
        }else{
            // 经过controller后获取数据的情况
            InputStream stream = ctx.getResponseDataStream();
            try {
                body = StreamUtils.copyToString(stream, UTF8);
            } catch(IOException e) {
                log.error("读取响应数据异常:{}", e.getMessage(), e);
                body = null;
            }
        }
        if (StringUtils.isNotBlank(body) && StringUtils.isNotBlank(priKey)){
            try{
                PrivateKey privateKey = RsaKeyHelper.getRsaPrivateKey(priKey);
                String newBody = RsaKeyHelper.encrypt(body, privateKey);
                if (log.isDebugEnabled()){
                    log.debug("加密返回数据, 原文:{},加密后:{}", body, newBody);
                }
                ctx.setSendZuulResponse(false);
                ctx.setResponseBody(newBody);
                ctx.getResponse().setContentType("text/plain;charset=UTF-8");
                // 添加特定响应头表示数据已加密
                ctx.getResponse().addHeader("X-RESPONSE-encrypt", "true");;
            }catch(Exception e){
                log.error("加密结果出现异常:"+e.getMessage(), e);
                throw new ZuulRuntimeException(new ZuulException(new ResponseEncryptException(), "系统异常", HttpStatus.INTERNAL_SERVER_ERROR.value(), "加密出现异常"));
            }
        }
        if (log.isDebugEnabled()){
            log.debug("响应报文加密处理结束");
        }
        return null;
    }
}
