package com.platform.system.gate.zuul;

/**
 * filter常量
 * @version: 1.0
 */
public interface FilterConstant {

    /**
     * 失败的filter
     */
    String FAILED_FILTER = "failed.filter";

    /**
     * 请求频率限制状态位
     * 值为字符串的true
     */
    String RATE_LIMIT_EXCEEDED = "rateLimitExceeded";

    /**
     * 请求时间记录缓存key
     */
    String REQUEST_TIME_LOG_KEY = "reqeustTimeLogKey";

    /**
     * 当前请求用户信息
     */
    String REQUEST_USER_INFO = "requestUserInfo";
}
