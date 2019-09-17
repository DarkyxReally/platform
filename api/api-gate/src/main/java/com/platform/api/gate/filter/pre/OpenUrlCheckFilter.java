package com.platform.api.gate.filter.pre;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;
import com.platform.api.gate.configuration.properties.GateIgnoreProperties;
import com.platform.api.gate.filter.FilterConstant;
import com.platform.system.gate.zuul.filter.pre.BasePreFilter;

/**
 * 开放的url过滤器
 * @version: 1.0
 */
@Component
public class OpenUrlCheckFilter extends BasePreFilter {

    public OpenUrlCheckFilter() {
        super(FilterConstant.OPEN_URL_CHECK);
    }
    
    public OpenUrlCheckFilter(int filterOrder) {
        super(filterOrder);
    }

    /** 不做登陆拦截  */
    @Autowired
    private GateIgnoreProperties ignore;
    
    /**
     * 是否全匹配
     * @param requestUri
     * @return
     */
    private boolean isContains(String requestUri){
        boolean flag = false;
        for(String uri : ignore.getContain()){
            if(StringUtils.isEmpty(uri)){
                continue;
            }
            uri = uri.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
            String regEx = "^" + uri + "$";
            flag = Pattern.compile(regEx).matcher(requestUri).find();
            if(flag){ return true; }
        }
        return flag;
    }

    /**
     * URI是否以什么打头
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri){
        boolean flag = false;
        for(String s : ignore.getStartWith()){
            if (StringUtils.isBlank(s)){
                continue;
            }
            if (s.endsWith("/")){
                if(requestUri.startsWith(s)){ return true; }
            }else{
                if(requestUri.startsWith(s + "/")){ return true; }
            }
        }
        return flag;
    }

    @Override
    public boolean shouldFilter(){
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestUri = request.getRequestURI();
        if(this.isStartWith(requestUri) || this.isContains(requestUri)){
            // 是开放的URL地址
            return true;
        }
        return false;
    }

    @Override
    public Object run(){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set(FilterConstant.REQUEST_IS_OPEN_URL);
        return null;
    }
}
