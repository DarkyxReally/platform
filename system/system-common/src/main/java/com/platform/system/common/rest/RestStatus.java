package com.platform.system.common.rest;

/**
 * REST请求状态码
 * 2XXXX: 20000 - 30000 成功表示<br>
 * 3XXXX 预留<br>
 * 4XXXX 40000-50000 业务失败<br>
 * 
 * 400XX 请求参数非法或权限相关<br>
 * 401XX 请求方式或请求头相关数据不正确<br>
 * 402XX 认证相关错误码(服务间TOKEN认证及用户TOKEN认证)<br>
 * 42XXX 具体的业务异常<br>
 * 420XX 相关业务通用状态<br>
 * 421XX 账户相关<br>
 * 422XX 产品相关<br>
 * 423XX 订单相关<br>
 * 424XX 支付相关<br>
 * 425XX 用户登陆相关<br>
 * 426XX 短信发送相关<br>
 * 427XX 用户动态相关状态<br>
 * 428XX 商户相关<br>
 * 429XX 用户实名认证相关状态<br>
 * 430XX 邀请相关<br>
 * 431XX 用户兑换相关状态<br>
 * 432XX 流量果相关<br>
 * 433XX 反流相关<br>
 * 434XX 红包相关<br>
 * 435XX 新手礼相关<br>
 * 436XX 活动相关<br>
 * 437XX 用户二维码相关<br>
 * 438XX App版本相关<br>
 * 439XX 挑战120相关<br>
 * 440XX 理财产品/信用卡相关<br>
 * 441XX 特惠充值相关<br>
 * 442XX 我爱猜成语相关<br>
 * 443XX VIP相关<br>
 * 444XX 拆话费相关<br>
 * 445XX 积分通兑相关<br>
 * 
 * 5XXXX 服务端异常<br>
 *
 * 621XX 有券商户相关
 * 622XX 有券订单相关
 * @version: 1.0
 */
public interface RestStatus {

    /**
     * the status codes of per restful request.
     *
     * @return 2xxxx if succeed, 4xxxx if business fail, 5xxxx if server side crash.
     */
    int code();

    /**
     * @return status enum name
     */
    String name();

    /**
     * @return message summary
     */
    String message();
    
    /** 是否打印错误堆栈*/
    boolean isLogErrorStack();
    
}
