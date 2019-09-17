package com.platform.auth.bs.interceptor.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.platform.auth.common.annotation.RestLog;
import com.platform.auth.common.config.AuthClientConfig;
import com.platform.auth.common.config.AuthUserConfig;
import com.platform.auth.common.util.IRequestDataLogService;
import com.platform.auth.common.util.RequestData;
import com.platform.auth.common.util.RestOperatorLog;
import com.platform.auth.model.constant.IInterceptorConstant;
import com.platform.system.common.annotation.PlatformSystem;
import com.platform.system.common.constant.RequestAttributeConst;
import com.platform.system.common.context.UserContextHandler;
import com.platform.system.common.context.user.IUserDetail;
import com.platform.system.common.enums.rest.AuthCode;
import com.platform.system.common.json.JsonUtil;
import com.platform.system.common.util.Shit;
import com.platform.system.common.util.WebUtil;
import com.platform.system.common.web.ServletContextHolder;


/**
 * 操作日志拦截器
 * @version: 1.0
 */
public class OperatorLogRestInterceptor extends HandlerInterceptorAdapter implements Ordered {

    private int order = IInterceptorConstant.OPERATOR_LOG_ORDER;

    @Autowired
    private IRequestDataLogService requestDataLogService;

    @Autowired
    private AuthUserConfig authUserConfig;

    @Autowired
    private AuthClientConfig clientConfig;

    public OperatorLogRestInterceptor() {

    }

    public OperatorLogRestInterceptor(int order) {
        this.order = order;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        // 配置该注解，说明不进行拦截
        RestLog restLog = handlerMethod.getMethodAnnotation(RestLog.class);
        if(null == restLog){
            // 没有注解, 不记录日志
            return true;
        }
        IUserDetail userDetail = UserContextHandler.get();
        if (null == userDetail){
            Shit.fatal(AuthCode.TOKEN_INVALID);
        }

        String systemCode = restLog.systemCode();
        if(StringUtils.isBlank(systemCode)){
            // 方法上有注解, 以方法注解为准
        	PlatformSystem system = handlerMethod.getMethodAnnotation(PlatformSystem.class);
            if(system != null){
                systemCode = system.code();
            }
            if(StringUtils.isBlank(systemCode)){
                // 方法上没有注解, 以类注解为准
                system = handlerMethod.getBeanType().getAnnotation(PlatformSystem.class);
                if(null != system){
                    if(StringUtils.isNotBlank(system.code())){
                        systemCode = system.code();
                    }
                }
            }
        }

        String moduleCode = restLog.moduleCode();
//        if(StringUtils.isBlank(moduleCode)){
//            // 方法上有注解, 以方法注解为准
//            Module module = handlerMethod.getMethodAnnotation(Module.class);
//            if(module != null){
//                moduleCode = module.code();
//            }
//            if(StringUtils.isBlank(moduleCode)){
//                // 方法上没有注解, 以类注解为准
//                module = handlerMethod.getBeanType().getAnnotation(Module.class);
//                if(null != module){
//                    if(StringUtils.isNotBlank(module.code())){
//                        moduleCode = module.code();
//                    }
//                }
//            }
//        }

        String requestURI = request.getRequestURI();
        Enumeration<String> headerNames = request.getHeaderNames();
        String userTokenHeader = authUserConfig.getTokenHeader();
        String clientTokenHeader = clientConfig.getTokenHeader();
        List<Map<String, String>> headers = new ArrayList<Map<String, String>>();
        while(headerNames.hasMoreElements()){
            String name = (String)headerNames.nextElement();
            Map<String, String> header = new HashMap<String, String>();
            String value = request.getHeader(name);
            if(StringUtils.equalsIgnoreCase(name, userTokenHeader)){
                // TOKEN 请求头隐私化处理
                value = "******";
            }else if(StringUtils.equalsIgnoreCase(name, RequestAttributeConst.AUTHORIZATION_HEADER)){
                // 用户信息头隐私化处理
                value = "******";
            }else if(StringUtils.equalsIgnoreCase(name, clientTokenHeader)){
                // 客户端信息头隐私化处理
                value = "******";
            }
            header.put(name, value);
            headers.add(header);
        }

        RequestData logRequest = new RequestData();
        logRequest.setDatetime(new Date());
        logRequest.setUrl(requestURI);
        // TODO 请求头中敏感信息处理

        logRequest.setHeader(JsonUtil.toJSONString(headers));
        // 请求参数中敏感信息处理
        logRequest.setParam(JsonUtil.toJSONString(request.getParameterMap()));
        String methodName = request.getMethod();
        logRequest.setMethod(methodName);
        String reqBody = null;
        if("POST".equalsIgnoreCase(methodName) || "PUT".equalsIgnoreCase(methodName)){
            reqBody = WebUtil.getRequestBodyString(request);
            // TODO 请求体中敏感信息处理
        }
        logRequest.setBody(reqBody);

        // 本次请求ID
        String requestId = ServletContextHolder.fetchRequestId();
        logRequest.setIdRequest(requestId);
        logRequest.setDescription(restLog.description());
        logRequest.setSystemCode(systemCode);
        logRequest.setModule(moduleCode);
        // 本次请求的ip
        logRequest.setIp(WebUtil.getIpAddress(request));
        // 用户ip
        String userIp = request.getHeader(RequestAttributeConst.USER_IP);
        if(StringUtils.isNotBlank(userIp)){
            logRequest.setUserIp(userIp);
        }

        // 当前用户id
        logRequest.setIdUser(userDetail.userId());
        RestOperatorLog.getInstance().setLogService(requestDataLogService).offerQueue(logRequest);
        return true;
    }

    @Override
    public int getOrder(){
        return this.order;
    }
}
