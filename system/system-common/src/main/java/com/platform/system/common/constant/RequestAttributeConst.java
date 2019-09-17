package com.platform.system.common.constant;

/**
 * REQUEST属性常量
 */
public final class RequestAttributeConst {
    
    /**
     * 日志详情
     */
    public static final String DETAILS_KEY = "X-Logs-Details";
    /**
     * 请求body
     */
    public static final String REQUEST_BODY_KEY = "X-Request-Body";
    
    /**
     * 请求ID
     */
    public static final String REQUEST_ID = "X-Request-Id";
    
    /**
     * 客户端token
     */
    public static final String CLIENT_TOKEN = "X-Client-Token";
    
    /**
     * 请求用户的IP地址
     */
    public static final String USER_IP = "X-Request-User-Ip";
    
    /**
     * 用户昵称
     */
    public static final String USER_NAME= "X-Request-Username";
    
    /**
     * 用户账号
     */
    public static final String USER_ACCOUNT = "X-Request-User-Account";
    
    /**
     * 用户ID
     */
    public static final String USER_ID = "X-Request-User-Id";
    
    /**
     * 认证请求头
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * 有券商户认证请求头
     */
    public static final String CUSTOMER_AUTH_HEADER = "Customer-Auth";

    private RequestAttributeConst() {
        throw new IllegalStateException("do not try to use reflection");
    }

}
