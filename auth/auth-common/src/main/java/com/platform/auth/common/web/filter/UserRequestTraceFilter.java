package com.platform.auth.common.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Preconditions;
import com.platform.auth.common.userdetail.IUserDetailService;
import com.platform.system.common.context.user.IUserDetail;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户请求溯源跟踪
 * @version: 1.0
 */
@Slf4j
public class UserRequestTraceFilter extends OncePerRequestFilter {

    public static final String USER_ID = "USER_ID";

    /**
     * 用户信息接口
     */
    private IUserDetailService userDetailService;

    public UserRequestTraceFilter(IUserDetailService userDetailService) {
        this.setUserDetailService(userDetailService);
    }

    public IUserDetailService getUserDetailService(){
        return userDetailService;
    }

    public void setUserDetailService(IUserDetailService userDetailService){
        Preconditions.checkNotNull(userDetailService, "用户信息接口不能为空");
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,IOException{
        log.info("用户请求溯源跟踪");
    	IUserDetail user = userDetailService.getUser(request);
        String userId = null;
        if(null != user){
            userId = user.userId();
        }
        try{
            MDC.put(USER_ID, userId);
            super.doFilter(request, response, filterChain);
        }finally{
            MDC.remove(USER_ID);
        }
    }
}
