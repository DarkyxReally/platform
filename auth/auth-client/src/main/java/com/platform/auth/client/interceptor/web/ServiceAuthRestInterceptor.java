package com.platform.auth.client.interceptor.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.platform.auth.client.jwt.ServiceAuthUtil;
import com.platform.auth.common.annotation.IgnoreClientToken;
import com.platform.auth.common.config.AuthClientConfig;
import com.platform.auth.common.exception.JwtIllegalArgumentException;
import com.platform.auth.common.exception.JwtSignatureException;
import com.platform.auth.common.util.jwt.IJWTInfo;
import com.platform.auth.model.constant.IInterceptorConstant;
import com.platform.system.common.enums.rest.AuthCode;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.util.Shit;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;


/**
 * 服务认证拦截器
 * @version: 1.0
 */
@Slf4j
public class ServiceAuthRestInterceptor extends HandlerInterceptorAdapter implements Ordered {
    
    private int order = IInterceptorConstant.SERVICE_AUTH_ORDER;
    
    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    @Autowired
    private AuthClientConfig clientConfig;

    
    public ServiceAuthRestInterceptor(){
        
    }
    
    public ServiceAuthRestInterceptor(int order){
        this.order = order;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        boolean init = clientConfig.isInit();
        if (!init){
            //配置未初始化, 返回服务正在注册中
            Shit.fatal(StatusCode.SERVICE_INITIALIZING);
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        // 配置该注解，说明不进行服务拦截
        IgnoreClientToken annotation = handlerMethod.getBeanType().getAnnotation(IgnoreClientToken.class);
        if(annotation == null){
            annotation = handlerMethod.getMethodAnnotation(IgnoreClientToken.class);
        }
        if(annotation != null){ return super.preHandle(request, response, handler); }

        // TODO 白名单检验
        
        
        String token = request.getHeader(clientConfig.getTokenHeader());
        if (StringUtils.isBlank(token)){
            log.info("客户端token不存在");
            Shit.fatal(AuthCode.CLIENT_INVALID);
            return false;
        }
        try{
            IJWTInfo tokenInfo = serviceAuthUtil.parseToken(token);
            if (tokenInfo != null){
                return super.preHandle(request, response, handler); 
            }
            
            Shit.fatal(AuthCode.CLIENT_INVALID);
            return false;
            // 暂时不做服务可访问性的校验,只要获取正常,就说明token合法
//            ClientJWTInfoWrapper clientInfo = new ClientJWTInfoWrapper(tokenInfo);
//            String uniqueName = clientInfo.getClientId();
//            // 判断请求来源的客户端允许访问本服务
//            for(String client : serviceAuthUtil.getAllowedClient()){
//                if(client.equals(uniqueName)){ 
//                    return super.preHandle(request, response, handler); 
//                }
//            }
        }catch(Exception e){
            // 统一异常码
            if (e instanceof ExpiredJwtException){
                log.info("token已过期");
                Shit.fatal(AuthCode.CLIENT_INVALID, "无效token");
            }
            if (e instanceof JwtSignatureException){
                log.info("token签名不正确");
                Shit.fatal(AuthCode.CLIENT_INVALID, "无效token");
            }
            if (e instanceof JwtIllegalArgumentException){
                log.info("token参数不正确");
                Shit.fatal(AuthCode.CLIENT_INVALID, "无效token");
            }
            if (e instanceof MalformedJwtException){
                Shit.fatal(AuthCode.CLIENT_INVALID, "无效token");
            }
                        
            throw e;
        }
      
    }

    @Override
    public int getOrder(){
        return this.order;
    }
}
