package com.platform.system.common.web.intercerpor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.platform.system.common.context.XssContextHandler;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.util.Shit;
import com.platform.system.common.web.annotations.IgnoreXss;

import lombok.extern.slf4j.Slf4j;


/**
 * 校验XSS拦截器
 * @version: 1.0
 */
@Slf4j
public class CheckXssInterceptor extends HandlerInterceptorAdapter implements Ordered{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        // 配置该注解，说明需要校验XSS
        IgnoreXss annotation = handlerMethod.getMethodAnnotation(IgnoreXss.class);
        if (null != annotation) {
            // 有注解, 继续请求处理
            return true;
        }else {
            if (Boolean.TRUE.equals(XssContextHandler.getHasXss())) {
                log.error("请求含有XSS注入嫌疑, 禁止访问");
                Shit.fatal(StatusCode.XSS_ATTACKS);
            }
            return true;
        }
    }

    @Override
    public int getOrder(){
        return 0;
    }

}
