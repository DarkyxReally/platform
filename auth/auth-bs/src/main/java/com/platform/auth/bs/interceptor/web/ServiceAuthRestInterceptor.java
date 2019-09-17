package com.platform.auth.bs.interceptor.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.platform.auth.common.annotation.IgnoreClientToken;
import com.platform.auth.common.config.AuthClientConfig;
import com.platform.auth.common.exception.JwtIllegalArgumentException;
import com.platform.auth.common.exception.JwtSignatureException;
import com.platform.auth.common.util.jwt.IJWTInfo;
import com.platform.auth.model.constant.IInterceptorConstant;
import com.platform.auth.service.impl.util.client.ClientTokenUtil;
import com.platform.system.common.enums.rest.AuthCode;
import com.platform.system.common.util.Shit;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务认证拦截
 * @version: 1.0
 */
@Slf4j
public class ServiceAuthRestInterceptor extends HandlerInterceptorAdapter implements Ordered {

    private int order = IInterceptorConstant.SERVICE_AUTH_ORDER;
    @Autowired
    private ClientTokenUtil clientTokenUtil;
    @Autowired
    private AuthClientConfig authClientConfig;

    public ServiceAuthRestInterceptor() {

    }

    public ServiceAuthRestInterceptor(int order) {
        this.order = order;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        log.info("=========进入服务认证拦截==========");
        // 配置该注解，说明不进行服务拦截
        IgnoreClientToken annotation = handlerMethod.getBeanType().getAnnotation(IgnoreClientToken.class);
        if(annotation == null){
            annotation = handlerMethod.getMethodAnnotation(IgnoreClientToken.class);
        }
        if(annotation != null){ return super.preHandle(request, response, handler); }
        String token = request.getHeader(authClientConfig.getTokenHeader());
        if(StringUtils.isBlank(token)){
            log.info("客户端token不存在");
            Shit.fatal(AuthCode.CLIENT_INVALID);
            return false;
        }
        try{
            IJWTInfo tokenInfo = clientTokenUtil.getInfoFromToken(token);
            if(tokenInfo != null){ return super.preHandle(request, response, handler); }

            Shit.fatal(AuthCode.CLIENT_INVALID);
        }catch(Exception e){
            // 统一异常码
            if(e instanceof ExpiredJwtException){
                log.info("token已过期");
                Shit.fatal(AuthCode.CLIENT_INVALID, "无效token");
            }
            if(e instanceof JwtSignatureException){
                log.info("token签名不正确");
                Shit.fatal(AuthCode.CLIENT_INVALID, "无效token");
            }
            if(e instanceof JwtIllegalArgumentException){
                log.info("token参数不正确");
                Shit.fatal(AuthCode.CLIENT_INVALID, "无效token");
            }
            if(e instanceof MalformedJwtException){
                Shit.fatal(AuthCode.CLIENT_INVALID, "无效token");
            }
            Shit.fatal(AuthCode.CLIENT_INVALID, "无效token");
            e.printStackTrace();
            throw e;
        }
		return false;
    }

    @Override
    public int getOrder(){
        return this.order;
    }
}
