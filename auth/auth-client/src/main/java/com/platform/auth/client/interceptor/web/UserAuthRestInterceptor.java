package com.platform.auth.client.interceptor.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.platform.auth.common.annotation.AuthUserStatus;
import com.platform.auth.common.userdetail.IUserDetailService;
import com.platform.auth.model.constant.IInterceptorConstant;
import com.platform.system.common.context.UserContextHandler;
import com.platform.system.common.context.user.AuthUserTypeConstant.AuthUserType;
import com.platform.system.common.context.user.IUserDetail;
import com.platform.system.common.enums.rest.AuthCode;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.exception.RestStatusException;

import lombok.extern.slf4j.Slf4j;


/**
 * 用户认证拦截器
 * @version: 1.0
 */
@Slf4j
public class UserAuthRestInterceptor extends HandlerInterceptorAdapter implements Ordered{

    private int order = IInterceptorConstant.USER_AUTH_ORDER;
    
    @Autowired
    private IUserDetailService userDetailService;
    
    public UserAuthRestInterceptor(){
        
    }
    
    public UserAuthRestInterceptor(int order){
        this.order = order;
    }
    
    /**
     * 校验用户
     */
    private void checkUser(AuthUserStatus authUser, HttpServletRequest request, HttpServletResponse response, IUserDetail user) throws Exception{
        // 存在配置
        if (!authUser.requireUser()){
            // 不需要认证用户
            return;
        }
        // 直接解析用户信息
        if (null == user){
            log.error("用户不存在");
            throw new RestStatusException(AuthCode.TOKEN_INVALID);
        }
        AuthUserType[] userType = authUser.userType();
        if (userType.length == 0){
            // 不需要校验用户类型
            return;
        }
        for(AuthUserType authUserType : userType){
            if (authUserType == user.userType()){
                return ;
            }
        }
        log.info("用户id:{}, 没有权限访问当前路径", user.userId());
        throw new RestStatusException(StatusCode.INVALID_REQUEST);
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        // 解析用户信息
        IUserDetail userInfo = userDetailService.getUser(request);
        if (null == userInfo){
            UserContextHandler.remove();
        }else{
            UserContextHandler.set(userInfo);
        }
        // 开始判断是否需要认证用户以及认证用户的类型
        // 优先级: 方法上的AuthUserStatus > 类上的AuthUserStatus
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        // 方法上认证用户状态的注解
        AuthUserStatus authUser = handlerMethod.getMethodAnnotation(AuthUserStatus.class);
        if(authUser != null){
            // 校验用户
            checkUser(authUser, request, response, userInfo);
            return true;
        }
        
        // 类上是否配置了认证用户状态的注解
        authUser = handlerMethod.getBeanType().getAnnotation(AuthUserStatus.class);
        if (authUser != null){
            // 校验用户
            checkUser(authUser, request, response, userInfo);
            return true;
        }
        if (null == userInfo){
            // 默认 没有用户信息时, 抛异常
            log.error("用户不存在");
            throw new RestStatusException(AuthCode.TOKEN_INVALID);
        }
        return true;
    }

    @Override
    public int getOrder(){
        return this.order;
    }
    
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        UserContextHandler.remove();
    }
}
