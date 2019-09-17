package com.platform.system.gate.zuul.filter.pre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.system.common.sort.SpellComparator;
import com.platform.system.common.util.WebUtil;
import com.platform.system.gate.exception.InvalidSignException;
import com.platform.system.gate.util.GateSignUtil;
import com.platform.system.gate.zuul.properties.RequestDataSignCheckProperties;
import com.platform.system.gate.zuul.properties.RequestDataSignCheckProperties.Policy;

/** 请求报文签名校验
 * 
 * @version: 1.0 
 * */
@Slf4j
public class RequestDataSignCheckPreFilter extends BasePreFilter {

    private final RouteLocator routeLocator;

    private final RequestDataSignCheckProperties properties;

    public RequestDataSignCheckPreFilter(int filterOrder, RouteLocator routeLocator, RequestDataSignCheckProperties properties) {
        super(filterOrder);
        this.routeLocator = routeLocator;
        this.properties = properties;
    }

    @Override
    public boolean shouldFilter() {
        if(!properties.isEnabled()) {
            if (log.isDebugEnabled()){
                log.debug("未开启签名校验");
            }
            return false;
        }
        if (properties.isEnableAll()){
            // 强制开启所有
            return true;
        }
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        Route route = route(request);
        Optional<RequestDataSignCheckProperties.Policy> policy = policy(route);
        if(policy.isPresent()) {
           boolean isEnable = policy.get().isEnabled();
            if (!isEnable){
                if (log.isDebugEnabled()){
                    log.debug("路由:{},未开启签名校验", route.getId());
                }
                return false;
            }else{
                // 路由开启了校验
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
//                        log.debug("版本为空,不启用签名校验功能");
//                    }
//                    return false;
//                }
//                if (!AppVersionUtils.isVersionAllow(version, minVersion)){
//                    // 版本号低于启用的版本号
//                    if (log.isDebugEnabled()){
//                        log.debug("当前版本号:{}, 低于签名校验的最低版本号", route.getId());
//                    }
//                    return false;
//                }
                return true;
            }else{
                if (log.isDebugEnabled()){
                    log.debug("版本号字段:[{}]或最低版本号:[{}]未配置, 默认不开启签名校验", versionField, minVersion);
                }
                return false;
            }
        }
    }

    private Route route(HttpServletRequest request) {
        String requestURI = getRequestUri(request);
        return routeLocator.getMatchingRoute(requestURI);
    }

    private Optional<Policy> policy(Route route) {
        if(route != null) { return properties.getPolicy(route.getId()); }
        return Optional.empty();
    }
    
    /** 获取签名验证KEY
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

    /** 获取签名字段
     * 
     * @param policy
     * @return */
    private String getSignField(Policy policy) {
        String fieldName = policy.getSignField();
        if(StringUtils.isBlank(fieldName)) {
            if(log.isDebugEnabled()) {
                log.debug("路由策略没有配置签名字段名, 将采用全局配置");
            }
            fieldName = properties.getSignField();
            if(StringUtils.isEmpty(fieldName)) {
                if(log.isDebugEnabled()) {
                    log.debug("全局配置没有设置签名字段名");
                }
            }
        }
        return fieldName;
    }

    /** 获取参与签名校验的请求头
     * 
     * @return */
    private List<String> getHeaders(Policy policy) {
        // 签名字段名
        List<String> headers = policy.getHeaders();
        if(CollectionUtils.isEmpty(headers)) {
            // 路由没有配置参与签名字段, 采用全局配置的
            if(log.isDebugEnabled()) {
                log.debug("路由策略没有配置参与签名字段, 将采用全局配置的参与签名字段");
            }
            headers = properties.getHeaders();
        }
        return headers;
    }

    /** 校验签名
     * 
     * @param request
     * @param signField 签名字段
     * @param allowEmptySignValue 是否允许空签名字段值
     * @param signKey 签名验证key
     * @param headers 参与签名计算的请求头
     * */
    private void check(HttpServletRequest request, String signField, boolean allowEmptySignValue, String signKey, List<String> headers) {
        String sign = request.getHeader(signField);
        if (StringUtils.isBlank(sign)){
            if (log.isDebugEnabled()){
                log.debug("签名字段值为空");
            }
            if (!allowEmptySignValue){
                if (log.isDebugEnabled()){
                    log.debug("不允许签名字段值为空");
                }
                // 不允许出现签名字段值为空的情况
                throw new ZuulRuntimeException(new ZuulException(new InvalidSignException(), "签名无效", HttpStatus.FORBIDDEN.value(), "签名无效"));
            }else{
                if (log.isDebugEnabled()){
                    log.debug("忽略签名校验");
                }
                return ;
            }
        }
        // 获取
        Map<String, List<String>> valuesMap = new HashMap<String, List<String>>();
        if(!CollectionUtils.isEmpty(headers)) {
            Set<String> headerSet = new HashSet<String>(headers);
            for(String header : headerSet) {
                if(StringUtils.isBlank(header)) {
                    continue;
                }
                if(header.equals(signField)) {
                    // 签名字段不参与
                    continue;
                }

                String key = header + "_h";
                List<String> list = valuesMap.get(key);
                if(null == list) {
                    list = new ArrayList<String>();
                    valuesMap.put(key, list);
                }
                Enumeration<String> enumeration = request.getHeaders(header);
                while(enumeration.hasMoreElements()) {
                    String value = enumeration.nextElement();
                    if(StringUtils.isNotBlank(value)) {
                        list.add(value);
                    }
                }
            }
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        if(!CollectionUtils.isEmpty(parameterMap)) {
            for(Entry<String, String[]> entry : parameterMap.entrySet()) {
                if(StringUtils.isBlank(entry.getKey())) {
                    continue;
                }
                String key = entry.getKey() + "_q";
                List<String> list = valuesMap.get(key);
                if(null == list) {
                    list = new ArrayList<String>();
                    valuesMap.put(key, list);
                }

                String[] values = entry.getValue();
                if(ArrayUtils.isNotEmpty(values)) {
                    for(String value : values) {
                        if(StringUtils.isNotBlank(value)) {
                            list.add(value);
                        }
                    }
                }
            }
        }

        SpellComparator comparator = new SpellComparator();
        // ◆ 参数名ASCII码从小到大排序（字典序）；
        // ◆ 如果参数的值为空不参与签名；
        // ◆ 参数名区分大小写；
        TreeMap<String, String> sortMap = new TreeMap<String, String>(comparator);
        for(Entry<String, List<String>> entry : valuesMap.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            if(CollectionUtils.isEmpty(value)) {
                continue;
            }

            Collections.sort(value, comparator);
            StringBuffer sb = new StringBuffer();
            for(String v : value) {
                if(StringUtils.isBlank(v)) {
                    sb.append(v);
                }
            }
            if(sb.length() == 0) {
                // 值为空, 不参与
                continue;
            }
            sortMap.put(key, sb.toString());
        }

        String body = null;
        String method = request.getMethod();
        if("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
            // POST或PUT请求
            String contentType = request.getContentType();
            if(StringUtils.isNotBlank(contentType) && !contentType.startsWith("multipart/form-data")) {
                // 非文件上传
                try {
                    body = WebUtil.getRequestBodyString(request);
                } catch(Exception e) {
                    log.error("读取请求body异常:{}, {}", e.getMessage(), e);
                }
            }
        }
        
        String signValue = GateSignUtil.signWithListValue(valuesMap, signKey, true, body);
        
        if(!signValue.equals(sign)) {
            if(log.isDebugEnabled()) {
                log.debug("签名无效");
            }
            throw new ZuulRuntimeException(new ZuulException(new InvalidSignException(), "签名无效", HttpStatus.FORBIDDEN.value(), "签名无效"));
        }
    }


    /** 根据策略校验
     * 
     * @param policy */
    private void checkByPolicy(Policy policy, HttpServletRequest request) {
        if (log.isDebugEnabled()){
            log.debug("根据路由策略进行签名校验");
        }
        // 签名字段
        String signField = getSignField(policy);
        // 签名KEY
        String signKey = getSignKey(policy);
        // 参与签名的请求头字段
        List<String> headers = getHeaders(policy);
        // 校验签名
        check(request, signField, policy.isAllowEmptySignValue(), signKey, headers);
    }

    /** 根据全局配置进行校验
     * 
     * @param request */
    private void checkByProperties(HttpServletRequest request) {
        if (log.isDebugEnabled()){
            log.debug("根据全局配置进行签名校验");
        }
        // 签名字段
        String signField = properties.getSignField();
        // 签名验证KEY
        String signKey = properties.getSignKey();
        // 参与签名的请求头字段
        List<String> headers = properties.getHeaders();
        // 校验签名
        check(request, signField, properties.isAllowEmptySignValue(), signKey, headers);
    }

    @Override
    public Object run() {
        if (log.isDebugEnabled()){
            log.debug("开始进行签名校验");
        }
        // 获取到request
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Route route = route(request);
        Optional<Policy> policy = policy(route);
        if(policy.isPresent()) {
            checkByPolicy(policy.get(), request);
        } else {
            checkByProperties(request);
        }
        if (log.isDebugEnabled()){
            log.debug("签名校验通过");
        }
        return null;
    }
}
