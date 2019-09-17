package com.platform.api.gate.filter.pre;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.platform.api.gate.configuration.properties.AppVersionProperties;
import com.platform.api.gate.exception.InvalidVersionKeyException;
import com.platform.system.common.constant.CommonConstants;
import com.platform.system.gate.zuul.FilterOrderConstant;
import com.platform.system.gate.zuul.filter.pre.BasePreFilter;

/**
 * 版本KEY验证过滤器
 * @version: 1.0
 */
@Slf4j
@Component
public class VersionKeyFilter extends BasePreFilter{
    
    public VersionKeyFilter() {
        super(FilterOrderConstant.VERSION_LIMIT_PRE_ORDER + 1);
    }

    public VersionKeyFilter(int filterOrder) {
        super(filterOrder);
    }

    @Autowired
    private AppVersionProperties versionProperties;
    
    /**
     * 传入的版本是否在指定的数据中
     * @version: 1.0
     * @param ver
     * @param vers
     * @return
     */
//    private static boolean isVersionIn(String ver, String[] vers){
//        for(String string : vers){
//            if (ver.matches(string)){
//                return true;
//            }
//        }
//        return false;
//    }
    
    /**
     * 请求地址是否需要校验api密钥
     * @version: 1.0
     * @param requestURI
     * @return
     */
//    private boolean isUriNeedCheckApiKey(String requestURI){
//        // 需要校验密钥的地址
//        List<String> needCheckUrl = versionProperties.getNeedCheckApiKeyUrl();
//        if (CollectionUtils.isNotEmpty(needCheckUrl)){
//            for(String url : needCheckUrl){
//                String uri = url.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
//                String regEx = "^" + uri + "$";
//                if (Pattern.compile(regEx).matcher(requestURI).find()){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    @Override
    public boolean shouldFilter(){
//        boolean enableCheckApiKey = versionProperties.isEnableCheckApiKey();
//        if (enableCheckApiKey){
//            RequestContext ctx = RequestContext.getCurrentContext();
//            HttpServletRequest request = ctx.getRequest();
//            String requestURI = request.getRequestURI();
//            return isUriNeedCheckApiKey(requestURI);
//        }
//        return enableCheckApiKey;
        return false;
    }
    
    /**
     * 获取密钥对应的版本
     * @version: 1.0
     * @param apiKey
     * @return
     */
//    private String getJoyApiKeyVersion(String apiKey){
//        Map<String, String> keyMap = versionProperties.getVersionKeyMap();
//        if (null == keyMap || keyMap.size() == 0){
//            return null;
//        }
//        
//        for(Entry<String, String> entry: keyMap.entrySet()){
//            String key = entry.getKey();
//            if (key.equals(apiKey)){
//                return entry.getValue();
//            }
//        }
//        return null;
//    }

    @Override
    public Object run(){
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String version = request.getHeader(CommonConstants.HEADER_VERSION);
        String requestURI = request.getRequestURI();
        if(StringUtils.isBlank(version)){
            if (log.isDebugEnabled()){
                log.debug("地址:{},认证API KEY, 请求头的版本号为空", requestURI);
            }
            throw new ZuulRuntimeException(new ZuulException(new InvalidVersionKeyException(), "非法请求, API KEY不匹配", HttpStatus.FORBIDDEN.value(), "API KEY不匹配"));
        }
//        if (AppVersionUtils.isVersionAllow(version, "2.1") && isUriNeedCheckApiKey(requestURI)){
//            // 版本号大于等于2.1且请求地址需要校验密钥
//            // 校验版本密钥
//            String apiKey = request.getHeader("joyapi");
//            if (StringUtils.isBlank(apiKey)){
//                if (log.isDebugEnabled()){
//                    log.debug("非法请求,密钥认证不存在");
//                }
//                throw new ZuulRuntimeException(new ZuulException(new InvalidVersionKeyException(), "非法请求, API KEY不匹配", HttpStatus.FORBIDDEN.value(), "API KEY不匹配"));
//            }else{
//                String apiVersion = getJoyApiKeyVersion(apiKey);
//                if (StringUtils.isBlank(apiVersion) || !isVersionIn(version, apiVersion.split("###"))){
//                    log.info("请求地址:{}, 密钥验证不通过", request.getRequestURI());
//                    throw new ZuulRuntimeException(new ZuulException(new InvalidVersionKeyException(), "非法请求, API KEY不匹配", HttpStatus.FORBIDDEN.value(), "API KEY不匹配"));
//                }
//            }
//        }
        return null;
    }
}
