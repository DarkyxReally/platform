package com.platform.auth.model.constant;

/**
 * 拦截器常量
 * @version: 1.0
 */
public interface IInterceptorConstant {

    /**
     * 服务间拦截器顺序
     */
    public static final int SERVICE_AUTH_ORDER = 1;

    /**
     * 用户认证拦截器
     */
    public static final int USER_AUTH_ORDER = 2;

    /**
     * XSS校验拦截器
     */
    public static final int CHECK_XSS_ORDER = 3;

    /**
     * 请求日志拦截器顺序
     */
    public static final int OPERATOR_LOG_ORDER = 4;
}
