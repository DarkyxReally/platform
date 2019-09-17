package com.platform.rabbitmq.constant;

/**
 * 队列常量
 * @version: 1.0
 */
public interface RoutingKeyConstant extends IRabbitConstant{

    /**
     * 路由前缀
     */
    String ROUTINGKEY_PREFIX = PREFIX + "routingKey.";
    
    /**
     * 消息推送路由
     */
    String MESSAGE_NOTICE_ROUTINGKEY = ROUTINGKEY_PREFIX + "messageNotice";

    /**
     * 添加角色路由
     */
    String ROLE_ADD_ROUTINGKEY = ROUTINGKEY_PREFIX + "roldAdd";

    /**
     * 发放新手礼路由
     */
    String NOVICE_ISSUE_ROUTINGKEY = ROUTINGKEY_PREFIX + "issueNovice";

    /**
     * 初始化M金钱包路由
     */
    String MFINANCE_INIT_ROUTINGKEY = ROUTINGKEY_PREFIX + "initMFinance";
    
    /**
     * 抢购路由
     */
    String QD_GOODS_ROUTINGKEY = ROUTINGKEY_PREFIX + "sexchange";
    
    /**
     * 达成流量大亨队列消息路由
     */
    String FOD_FLOW_ACHIEVEMENT_SUCCESS_ROUTING_KEY = ROUTINGKEY_PREFIX + "fod.achievement.success";
    
    /**
     * 手机号充值成功消息路由
     */
    String USER_PHONE_RECHARGE_SUCCESS_ROUTING_KEY = ROUTINGKEY_PREFIX + "phone.recharge.success";

    /**
     * 推送所有监听用户手机号注册的队列路由
     */
    String USER_PHONE_REGISTER_ROUTING_KEY = PREFIX + "phone.registered.success";

    /**
     * 用户手机号注册赠送充值券路由(给注册的手机号赠送充值券)
     */
    String USER_PHONE_REGISTER_ROUTING_KEY_SEND_COUPON = PREFIX + "phone.registered.sendCoupon";
    
    /**
     * 用户手机号注册给邀请者赠送充值券路由(给邀请者赠送)
     */
    String USER_PHONE_REGISTER_ROUTING_KEY_SEND_INVITER_COUPON = PREFIX + "phone.registered.sendInviterCoupon";

    /**
     * 第三方用户解绑消息
     */
    String USER_3RD_UNBIND_ROUTING_KEY = ROUTINGKEY_PREFIX + "user.3rd.unbind";
    
    /**
     * 用户手机号预注册路由
     */
    String USER_PHONE_PRE_REGISTE_ROUTING_KEY = ROUTINGKEY_PREFIX + "user-phone.pre-registe";

    /**
     * 领取活动奖品消息路由
     */
    String ACTIVITY_RECEIVE_REWARD_ROUTING_KEY = ROUTINGKEY_PREFIX + "activity.receive.reward";
    
    /**
     * 拆话费收礼人消息路由
     */
    String TELE_DISCHARGE_SEND_GIFT_IDUSER_KEY = ROUTINGKEY_PREFIX + "teleDischarge.sendGift";
    
    /**
     * 用户关注路由
     */
    String USER_ATTENTION_ROUTING_KEY = ROUTINGKEY_PREFIX + "user-attention";

    /**
     * 用户资料任务路由
     */
    String USER_INFO_TASK_ROUTING_KEY = ROUTINGKEY_PREFIX + "user-info-task";

    /**
     * 七牛文件路由
     */
    String QINIU_FILE_ROUTING_KEY = ROUTINGKEY_PREFIX + "qiniu.file";

    /**
     * 动态文件信息回调路由
     */
    String DYNAMIC_FILE_INFO_CALLBACK = ROUTINGKEY_PREFIX + "dynamic.file.info.callback";

    /**
     * 广告文件信息回调路由
     */
    String AD_FILE_INFO_CALLBACK = ROUTINGKEY_PREFIX + "ad.file.info.callback";
    
    /**
     * 路由: 去锁定APP用户
     */
    String APP_USER_TOLOCK_ROUTING_KEY = ROUTINGKEY_PREFIX + "routingkey.appUser.tolock";
    
    /**
     * 路由: 销毁token
     */
    String TOKEN_DESTROY_ROUTING_KEY = ROUTINGKEY_PREFIX + "routingkey.token.destroy";
    
    /**
     * 路由: 定向流量账号开通
     */
    String DIRECT_FLOW_OPEN_ACCOUNT_ROUTING_KEY = ROUTINGKEY_PREFIX + "direct_flow.account.open";
    
    /**
     * 商户已创建
     */
    String MERCHANT_CREATED = ROUTINGKEY_PREFIX + "merchant.created";
    
    /**
     * 品牌已创建
     */
    String BRAND_CREATED = ROUTINGKEY_PREFIX + "brand.created";

    /**
     * 商户加入品牌
     */
    String MERCHANT_JOIN_BRAND = ROUTINGKEY_PREFIX + "merchant.joinbrand";
    
    /**
     * 商户会员加入品牌会员
     */
    String MERCHANT_MEMBER_JOIN_BRAND_MEMBER = ROUTINGKEY_PREFIX + "merchant.member_join_brand_member";
}
