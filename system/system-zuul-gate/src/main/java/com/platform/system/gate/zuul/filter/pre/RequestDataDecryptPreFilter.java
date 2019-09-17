package com.platform.system.gate.zuul.filter.pre;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.Optional;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.platform.system.common.constant.CommonConstants;
import com.platform.system.common.util.RsaKeyHelper;
import com.platform.system.common.util.WebUtil;
import com.platform.system.gate.exception.RequestDecryptException;
import com.platform.system.gate.zuul.properties.RequestDataDecryptProperties;
import com.platform.system.gate.zuul.properties.RequestDataDecryptProperties.Policy;

/** 请求报文解密
 * 
 * */
@Slf4j
public class RequestDataDecryptPreFilter extends BasePreFilter {

    private final RouteLocator routeLocator;

    private final RequestDataDecryptProperties properties;

    public RequestDataDecryptPreFilter(int filterOrder, RouteLocator routeLocator, RequestDataDecryptProperties properties) {
        super(filterOrder);
        this.routeLocator = routeLocator;
        this.properties = properties;
    }

    @Override
    public boolean shouldFilter(){
        if(!properties.isEnabled()){
            if(log.isDebugEnabled()){
                log.debug("未开启报文解密功能");
            }
            return false;
        }

        // 功能开启
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String method = request.getMethod();
        if(!"POST".equalsIgnoreCase(method) && !"PUT".equalsIgnoreCase(method)){
            // 仅限POST和PUT请求进行解秘
            return false;
        }
        String contentType = request.getContentType();
        if(StringUtils.isNotBlank(contentType) && contentType.startsWith("multipart/form-data")){
            if(log.isDebugEnabled()){
                log.debug("上传文件操作不需要解密");
            }
            // 如果是文件上传 不处理数据解密
            return false;
        }

        if(properties.isEnableAll()){
            // 强制开启所有
            return true;
        }
        if(!"true".equalsIgnoreCase(request.getHeader("X-REQUEST-encrypt"))){
            // 没有加密, 不需要解密
            if(log.isDebugEnabled()){
                log.debug("报文没有加密, 不需解密处理");
            }
            return false;
        }

        Route route = route(request);
        Optional<RequestDataDecryptProperties.Policy> policy = policy(route);
        if(policy.isPresent()){
            boolean isEnable = policy.get().isEnabled();
            if(!isEnable){
                if(log.isDebugEnabled()){
                    log.debug("路由:{},未开启报文解密功能", route.getId());
                }
                return false;
            }else{
                return true;
            }
        }else{
            // 没有特定路由指定, 默认根据版本号来
            String versionField = properties.getVersionField();
            String minVersion = properties.getMinVersion();
            if(StringUtils.isNotBlank(versionField) && StringUtils.isNotBlank(minVersion)){
//                String version = request.getHeader(versionField);
//                if(StringUtils.isBlank(version)){
//                    if(log.isDebugEnabled()){
//                        log.debug("版本为空,不启用签名校验功能");
//                    }
//                    return false;
//                }
//                if(!AppVersionUtils.isVersionAllow(version, minVersion)){
//                    // 版本号低于启用的版本号
//                    if(log.isDebugEnabled()){
//                        log.debug("当前版本号:{}, 低于签名校验的最低版本号", route.getId());
//                    }
//                    return false;
//                }
                return true;
            }else{
                if(log.isDebugEnabled()){
                    log.debug("版本号字段:[{}]或最低版本号:[{}]未配置, 默认不开启签名校验", versionField, minVersion);
                }
                return false;
            }
        }
    }

    private Route route(HttpServletRequest request){
        String requestURI = getRequestUri(request);
        return routeLocator.getMatchingRoute(requestURI);
    }

    private Optional<RequestDataDecryptProperties.Policy> policy(Route route){
        if(route != null){ return properties.getPolicy(route.getId()); }
        return Optional.empty();
    }

    /** 获取解密密钥
     * 
     * @param policy
     * @return */
    private String getPriKey(Policy policy){
        String key = policy.getPriKey();
        if(StringUtils.isBlank(key)){
            if(log.isDebugEnabled()){
                log.debug("路由策略没有配置解密密钥, 将采用全局配置");
            }
            key = properties.getPriKey();
            if(StringUtils.isEmpty(key)){
                if(log.isDebugEnabled()){
                    log.debug("全局配置没有设置解密密钥");
                }
            }
        }
        return key;
    }

    @Override
    public Object run(){
        if(log.isDebugEnabled()){
            log.debug("开始进行数据解密处理");
        }
        // 获取到request
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Route route = route(request);
        Optional<Policy> policy = policy(route);
        // 解密密钥
        String key = null;
        if(policy.isPresent()){
            // 路由策略存在, 使用路由指定的配置
            key = getPriKey(policy.get());
        }else{
            key = properties.getPriKey();
        }
        if(StringUtils.isEmpty(key)){
            // / 没有解密密钥数据
            if(log.isDebugEnabled()){
                log.debug("解密密钥数据不存在");
            }
            return null;
        }
        String body = null;
        try{
            body = WebUtil.getRequestBodyString(request);
        }catch(Exception e){
            log.error("读取请求body异常:{}, {}", e.getMessage(), e);
        }
        if(StringUtils.isEmpty(body)){
            if(log.isDebugEnabled()){
                log.debug("没有请求体数据");
            }
            // 没有请求体
            return null;
        }

        String newBody = null;
        try{
            PrivateKey privateKey = RsaKeyHelper.getRsaPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKkjX6O44m5ixv4Pl2VOrXZLzg4b8KkRAqJKTbNqYkxkRVbIKTkMa6Vc/Dm7le52IrFKOF2Ats5SXaV8gEspJs97gYXugG00LXwDmnCD0AXtzk+arbMD5lueEIUDuBqrw9ceimDIPnBwGjaWmwKh1g1gYqdaCa5/4bHz1HI6p+tHAgMBAAECgYAnYBXOzqbgHdzHnqeXLRbS2FjzDewb39rmHzgxkY2qzRkbV+PI5SxWRexuIE6KQjRXY7j1g5aLN7+XXAGd/cmL2Ew5d1iV9FEdgDDSQ99DShdUrr9rk9sOi17cNqeLyPMt+Ixs08iyZ571a5q5EAlC5b8apuIa5UkKFY1YkK1ooQJBAOtlOyOPTFtMUzzJ/ohTJsaRVRaLN/7nQsNGiO5qFYE3NYKPKUEix7CBcVNQpW6Gg18e2bGpa7ml6D7t+KdTd3ECQQC38XEsh100G1X1NAnwltfZMBRFWGL4ZsTZ02ehEAfPGneQsvWdmaJVXJU7O+w68JY4VYhCXNdyuYIVShuUAGI3AkAXyAFihU6khqB+KrYRVb08IGClR2lf0kNXcR/SXltP06EPUKFNWFMuTNWDIkmkUeihyn8ZyND+T3mYKfiOBwuxAkAcm9e/VjpfzVScM4DMQ5nEtN6iPnH+MnWxb1jxyv1776DjB4J+BZQi9KhdMx+1tw+WKW9s6uWzeTjLOOB7L2gXAkEAr560kBqz4PwsYxAZRozc5Z5TKajHoHClIcO6WCP66hd2QMf+1gw/R7GPmWaBVCPm3ryJijXOW8aIiZNqjxJpJA==");
            newBody = RsaKeyHelper.decrypt(body, privateKey);
        }catch(Exception e){
            log.error("解密请求报文异常:" + e.getMessage(), e);
            throw new ZuulRuntimeException(new ZuulException(new RequestDecryptException(), "系统异常", HttpStatus.OK.value(), "解密数据出现异常"));
        }

        if(log.isDebugEnabled()){
            log.debug("密文:{},解密后:{}", body, newBody);
        }
        if(StringUtils.isBlank(newBody)){ return null; }
        final byte[] reqBodyBytes = newBody.getBytes(CommonConstants.UTF_8);
        // 重写上下文的HttpServletRequestWrapper
        ctx.setRequest(new HttpServletRequestWrapper(request){

            @Override
            public ServletInputStream getInputStream() throws IOException{
                return new ServletInputStreamWrapper(reqBodyBytes);
            }

            @Override
            public int getContentLength(){
                return reqBodyBytes.length;
            }

            @Override
            public long getContentLengthLong(){
                return reqBodyBytes.length;
            }
        });

        return null;
    }
}
