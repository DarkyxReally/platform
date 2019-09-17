package com.platform.api.gate.filter;



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
     * 防止重复请求filter的执行状态
     * 值为true代表已执行重复请求filter,false或null代表未执行
     */
    String REPEAT_REQUEST_EXCEEDED_STATUS = "repeatRequestExceeded";
    
    /**
     * 用于控制重复请求filter的key
     */
    String REPEAT_REQUEST_EXCEEDED_KEY = "repeatRequestExceededKey";
    
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
     * 请求的地址是开放的地址标志
     */
    String REQUEST_IS_OPEN_URL = "requestIsOpenUrl";
    
    /**
     * 请求的地址是需要签名的标志
     */
    String REQUEST_IS_NEED_SIGN = "requestIsNeedSign";
    
    /**
     * 版本无效
     */
    String REQUEST_VERSION_INVALID = "requestVersionInvalid";
    /**
     * 版本过低
     */
    String REQUEST_VERSION_IS_LOWER = "requestVersionLower";
    
    /**
     * 当前请求用户信息
     */
    String REQUEST_USER_INFO = "requestUserInfo";
    
    /**
     * 请求token无效
     */
    String REQUEST_INVALID_TOKEN = "requestInvalidToken";

    /**
     * IP地址被拒绝
     */
    String REQUEST_IP_INVALID = "requestIpInvalid";
    
    /**
     * 签名无效被拒绝
     */
    String REQUEST_SIGN_INVALID = "requestSignInvalid";
    
    /**
     * 缺少必要的参数
     */
    String REQUEST_PARAMS_MISS_REQUIRED = "requestParamMissRequired";
    
    /**
     * 参数错误
     */
    String REQUEST_PARAMS_ERROR = "requestParamErro";
    
    /**
     * 重复请求
     */
    String REQUEST_IS_DUPLICATE = "requestIsDuplicate";
    
    /**
     * 重放攻击
     */
    String REQUEST_IS_REPLAY_ATTACKS = "requestIsReplayAttacks";

    /**
     * 请求溯源过滤器顺序
     */
    int FILTER_ORDER_TRACE = -99999999;
    
    /**
     * 开放地址检测
     */
    int OPEN_URL_CHECK = -3;
    
    /**
     * 校验用户(需要放在限流器（-1）之前执行,否则会导致限流器取不到用户信息而把用户当作匿名用户处理)
     */
    int USER_CHECK = -2;
    
    /**
     * 签名校验过滤器顺序
     */
    int PRE_FILTER_ORDER_SIGN_CHECK = FILTER_ORDER_TRACE + 1;
    
    /**
     * 开放的url过滤器执行顺序
     */
    int PRE_FILTER_ORDER_OPEN_URL_CHECK = FILTER_ORDER_TRACE +1 ;
    
    /**
     * 版本检测过滤器执行顺序, 放在开放地址检测之后
     */
    int PRE_FILTER_ORDER_VERSION_CHECK = PRE_FILTER_ORDER_OPEN_URL_CHECK + 1;
    
    /***
     * 用户token校验过滤器执行顺序, 放在版本检测之后
     */
    int PRE_FILTER_ORDER_USER_TOKEN_CHECK = PRE_FILTER_ORDER_VERSION_CHECK + 1;

    /**
     * 商户收银员校验过去起执行顺序, 放在用户token校验过滤器之后
     */
    int PRE_FILTER_ORDER_CASHIER_TOKEN_CHECK = PRE_FILTER_ORDER_USER_TOKEN_CHECK + 1;
    
    /**
     * 客户端token过滤器执行顺序, 放在用户token校验过滤器之后
     */
    int PRE_FILTER_ORDER_CLIENT_TOKEN_CHECK = PRE_FILTER_ORDER_CASHIER_TOKEN_CHECK + 1;
}
