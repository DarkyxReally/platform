package com.platform.system.gate.zuul.filter.post;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.util.GuidGenerator;
import com.platform.system.gate.exception.ResponseSignException;
import com.platform.system.gate.util.GateSignUtil;
import com.platform.system.gate.zuul.properties.ResponseDataSignProperties;
import com.platform.system.gate.zuul.properties.ResponseDataSignProperties.Policy;

/** 响应数据签名拦截器
 * 
 * @version: 1.0 */
@Slf4j
public class ResponseDataSignPostFilter extends BasePostFilter {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private final RouteLocator routeLocator;

    private final ResponseDataSignProperties properties;

    public ResponseDataSignPostFilter(int filterOrder, RouteLocator routeLocator, ResponseDataSignProperties properties) {
        super(filterOrder);
        this.routeLocator = routeLocator;
        this.properties = properties;
    }

    @Override
    public boolean shouldFilter() {
        if(!properties.isEnabled()) {
            if (log.isDebugEnabled()){
                log.debug("未开启响应报文签名");
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
        Optional<ResponseDataSignProperties.Policy> policy = policy(route);
        if(policy.isPresent()) {
            boolean enabled = policy.get().isEnabled();
            if (!enabled){
                if (log.isDebugEnabled()){
                    log.debug("路由:{},未开启响应报文签名", route.getId());
                }
                return false;
            }
            return true;
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
        if(route != null) { return properties.getPolicy(route.getId()); }
        return Optional.empty();
    }

    /** 获取签名验证公钥
     * 
     * @param policy
     * @return */
    private String getSignKey(Policy policy) {
        String key = policy.getSignKey();
        if(StringUtils.isBlank(key)) {
            if(log.isDebugEnabled()) {
                log.debug("路由策略没有配置签名验证KEY, 将采用全局配置");
            }
            key = properties.getSignKey();
            if(StringUtils.isEmpty(key)) {
                if(log.isDebugEnabled()) {
                    log.debug("全局配置没有设置签名验证KEY");
                }
            }
        }
        return key;
    }
    
    /**
     * 获取参与签名的请求头
     * @param policy
     * @return
     */
    private List<String> getJoinSignHeaders(Policy policy){
        List<String> joinSignHeaders = policy.getJoinSignHeaders();
        if(CollectionUtils.isEmpty(joinSignHeaders)) {
            if(log.isDebugEnabled()) {
                log.debug("路由策略没有配置参与签名的请求头, 将采用全局配置");
            }
            joinSignHeaders = properties.getJoinSignHeaders();
            if(CollectionUtils.isEmpty(joinSignHeaders)) {
                if(log.isDebugEnabled()) {
                    log.debug("全局配置没有设置参与签名的请求头");
                }
            }
        }
        return joinSignHeaders;
    }

    @Override
    public Object run() {
        if (log.isDebugEnabled()){
            log.debug("开始进行响应报文签名处理");
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Route route = route(request);
        Optional<Policy> policy = policy(route);
        List<String> joinSignHeaders;
        String signKey;
        if(policy.isPresent()) {
            signKey = getSignKey(policy.get());
            joinSignHeaders = getJoinSignHeaders(policy.get());
        } else {
            signKey = properties.getSignKey();
            joinSignHeaders = properties.getJoinSignHeaders();
        }
        try{
            String responseBody = ctx.getResponseBody();
            String body;
            if(StringUtils.isNotEmpty(responseBody)) {
                // 没有经过control 直接从Filter中抛出异常时的情况
                body = responseBody;
            } else {
                // 经过controller后获取数据的情况
                InputStream stream = ctx.getResponseDataStream();
                try {
                    body = StreamUtils.copyToString(stream, UTF8);
                    ctx.setResponseBody(body);
                } catch(IOException e) {
                    log.error("读取响应数据异常:{}", e.getMessage(), e);
                    body = null;
                }
            }

            HttpServletResponse response = ctx.getResponse();
            String timestamp = System.currentTimeMillis() + "";
            String noncestr = GuidGenerator.generate32() + "";
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("timestamp_h", System.currentTimeMillis() + "");
            headers.put("noncestr_h", noncestr);
            if (CollectionUtils.isNotEmpty(joinSignHeaders)){
                for(String headerName : joinSignHeaders) {
                    String headerValue = response.getHeader(headerName);
                    if (StringUtils.isNotBlank(headerValue)){
                        headers.put(headerName, headerValue);
                    }
                }
            }
            
            String sign = GateSignUtil.sign(headers, signKey, false, body);
            response.addHeader("X-RESPONSE-noncestr", noncestr);
            response.addHeader("X-RESPONSE-timestamp", timestamp);
            response.addHeader("X-RESPONSE-sign", sign);
            if (log.isDebugEnabled()){
                log.debug("签名结果responseBody:{}, 头信息:{}, 签名值:{}", body, headers, sign);
            }
        }catch(Exception e){
            log.error("响应数据签名处理异常:"+e.getMessage(), e);
            throw new ZuulRuntimeException(new ZuulException(new ResponseSignException(), "系统异常", HttpStatus.INTERNAL_SERVER_ERROR.value(), "加密出现异常"));
        }
        if (log.isDebugEnabled()){
            log.debug("响应报文签名结束");
        }
        return null;
    }
}
