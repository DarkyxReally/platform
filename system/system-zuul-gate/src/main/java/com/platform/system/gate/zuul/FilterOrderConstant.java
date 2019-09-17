package com.platform.system.gate.zuul;

/** 过滤器 顺序常量
 * 
 * @version: 2.6.0 */
public interface FilterOrderConstant {

    // 前置过滤器执行顺序 ====================开始=====================
    /** 请求溯源前置过滤器顺序 */
    int TRACE_PRE_ORDER = 0;

    /** 请求信息日志记录前置过滤器顺序 */
    int REQUEST_INFO_LOG_PRE_ORDER = 1;
    
    /** IP限制过滤器 */
    int IP_LIMIT_PRE_ORDER = 2;

    /** 请求头值前置校验过滤器顺序 */
    int HEADER_VALUE_CHECK_PRE_ORDER = 3;

    /** 版本检测前置校验过滤器顺序 */
    int VERSION_LIMIT_PRE_ORDER = 4;

    /** 统一签名前置校验过滤器顺序 */
    int UNIFIED_SIGN_CHECK_PRE_ORDER = 5;

    /** 请求报文签名前置校验过滤器顺序 */
    int REQUEST_DATA_SIGN_CHECK_PRE_ORDER = 6;

    /** 请求报文解密前置过滤器顺序 */
    int REQUEST_DATA_DECRYPT_PRE_ORDER = 7;

    /** 请求统计前置过滤器顺序 */
    int REQUEST_STAT_PER_ORDER = 8;

    /** IP用户统计前置校验过滤器顺序 */
    int IP_USER_STAT_PRE_ORDER = 9;

    /** 请求时间统计前置校验过滤器顺序 */
    int REQUEST_TIME_STAT_PRE_ORDER = 10;
    // 前置过滤器执行顺序 ====================结束=====================

    // 后置过滤器执行顺序 ====================开始=====================
    /** 响应报文加密后置处理顺序 */
    int RESPONSE_DATA_ENCRYPT_POST_ORDER = 997;

    /** 响应报文签名后置处理顺序 */
    int RESPONSE_DATA_SIGN_POST_ORDER = 998;

    /** 请求时间统计后置处理顺序 */
    int REQUEST_TIME_STAT_POST_ORDER = 999;

    // 后置过滤器执行顺序 ====================结束=====================

    // 异常处理顺序 ====================开始=====================
    // 前置异常处理顺序==================开始=====================
    /** 默认的前置异常处理顺序 */
    int DEFAULT_PRE_ERROR_ORDER = -1;

    /** 频率限制异常处理顺序 */
    int RATE_LIMIT_PRE_ERROR_ORDER = -2;

    /** 重复请求异常处理顺序 */
    int DUPLICATE_REQUEST_PRE_ERROR_ORDER = -3;

    /** 版本检测过滤器异常处理顺序 */
    int VERSION_LIMIT_PRE_ERROR_ORDER = -4;

    /** 请求头值校验异常处理顺序 */
    int HEADER_VALUE_CHECK_PRE_ERROR_ORDER = -5;

    /** 重放攻击 异常处理 */
    int REPLAY_ATTACKS_PRE_ERROR_ORDER = -6;

    /** 签名无效异常处理顺序 */
    int INVALID_SIGN_PRE_ERROR_ORDER = -7;

    /** 参数格式不正确 异常处理 */
    int PARAMS_ERROR_PRE_ERROR_ORDER = -8;

    /** 缺少必要参数 异常处理 */
    int MISS_PARAMS_PRE_ERROR_ORDER = -9;

    /** 请求报文解密 异常处理 */
    int REQUEST_DATA_DECRYPT_PRE_ERROR_ORDER = -10;

    /** IP地址无效 异常处理 */
    int IP_LIMIT_PRE_ERROR_ORDER = -11;
    // 前置异常处理顺序==================结束=====================
    
    // 后置异常处理顺序==================开始=====================
    /** 默认的后置异常处理顺序　 */
    int DEFAULT_POST_ERROR_ORDER = -1;
    
    /** 响应报文加密异常处理顺序 */
    int RESPONSE_DATA_ENCRYPT_POST_ERROR_ORDER = -2;
    
    /** 响应报文签名异常处理顺序 */
    int RESPONSE_DATA_SIGN_POST_ERROR_ORDER = -3;

    // 后置异常处理顺序==================结束=====================
    // 路由异常处理顺序
    /** 路由异常处理 */
    int DEFAULT_ROUTE_ERROR_ORDER = -1;
    // 异常处理顺序 ====================结束=====================
}
