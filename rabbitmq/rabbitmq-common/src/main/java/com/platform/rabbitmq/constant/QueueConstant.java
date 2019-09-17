package com.platform.rabbitmq.constant;

/**
 * 队列常量
 * @version: 1.0
 */
public interface QueueConstant extends IRabbitConstant {

    /**
     * 队列前缀
     */
    String QUEUE_PREFIX = "platform.";

    /**
     * 消息推送队列
     */
    String MESSAGE_NOTICE_QUEUE = QUEUE_PREFIX + "queue.messageNotice";

    /**
     * 交换机: direct模式消息推送
     */
    String MESSAGE_NOTICE_EXCHANGE = QUEUE_PREFIX + "exchange.messageNotice";

    /**
     * 交换机: 主题模式推送
     */
    String EXCHANGE_LIGUJOY_TOPIC = QUEUE_PREFIX + "exchange.topic";

    /**
     * 延迟交换机
     */
    String DELAYED_TOPIC_EXCHANGE = QUEUE_PREFIX + "delayed.exchange.topic";

    /**
     * 消息推送路由
     */
    String MESSAGE_NOTICE_ROUTINGKEY = QUEUE_PREFIX + "routingkey.messageNotice";

    /**
     * 消息推送交换机
     */
    String ROLE_ADD_EXCHANGE = QUEUE_PREFIX + "exchange.roldAdd";
    /**
     * 添加角色队列
     */
    String QUEUE_ROLE_ADD = QUEUE_PREFIX + "queue.roldAdd";
    /**
     * 添加角色路由
     */
    String ROLE_ADD_ROUTINGKEY = QUEUE_PREFIX + "routingkey.roldAdd";

    /**
     * 新手礼交换机
     */
    String NOVICE_EXCHANGE = QUEUE_PREFIX + "exchange.noviceAward";

    /**
     * 发放新手礼路由
     */
    String NOVICE_ISSUE_ROUTINGKEY = QUEUE_PREFIX + "routingKey.issueNovice";

    /**
     * 发放新手礼队列
     */
    String NOVICE_ISSUE_QUEUE = QUEUE_PREFIX + "queue.issuseNovice";

    /**
     * M金钱包交换机
     */
    String MFINANCE_EXCHANGE = QUEUE_PREFIX + "exchange.mFinance";

    /**
     * 初始化M金钱包路由
     */
    String MFINANCE_INIT_ROUTINGKEY = QUEUE_PREFIX + "routingKey.initMFinance";

    /**
     * 初始化M金钱包队列
     */
    String MFINANCE_INIT_QUEUE = QUEUE_PREFIX + "queue.initMFinance";

    /**
     * 抢购队列
     */
    String QD_GOODS_QUEUE = QUEUE_PREFIX + "queue.sexchange";
    /**
     * 抢购路由
     */
    String QD_GOODS_ROUTINGKEY = QUEUE_PREFIX + "routingKey.sexchange";

    /**
     * 达成流量大亨队列
     */
    String FOD_FLOW_ACHIEVEMENT_SUCCESS_QUEUE = QUEUE_PREFIX + "queue.fod.achievement.success";

    /**
     * 达成流量大亨队列消息路由
     */
    String FOD_FLOW_ACHIEVEMENT_SUCCESS_ROUTING_KEY = QUEUE_PREFIX + "routingKey.fod.achievement.success";

    /**
     * 手机号充值成功消息队列
     */
    String USER_PHONE_RECHARGE_SUCCESS_QUEUE = QUEUE_PREFIX + "queue.phone.recharge.success";

    /**
     * 手机号充值成功消息路由
     */
    String USER_PHONE_RECHARGE_SUCCESS_ROUTING_KEY = QUEUE_PREFIX + "routingKey.phone.recharge.success";

    /**
     * 推送所有监听用户手机号注册的队列路由
     */
    String USER_PHONE_REGISTER_ROUTING_KEY = QUEUE_PREFIX + "phone.registered.success";

    /**
     * 用户手机号注册赠送充值券路由(给注册的手机号赠送充值券)
     */
    String USER_PHONE_REGISTER_ROUTING_KEY_SEND_COUPON = QUEUE_PREFIX + "phone.registered.sendCoupon";

    /**
     * 用户手机号注册给邀请者赠送充值券路由(给邀请者赠送)
     */
    String USER_PHONE_REGISTER_ROUTING_KEY_SEND_INVITER_COUPON = QUEUE_PREFIX + "phone.registered.sendInviterCoupon";

    /**
     * 礼物分享 手机号预发放礼物的队列路由
     */
    String USER_PHONE_GIFT_SHARE_PRE_REWARD_ROUTING_KEY = QUEUE_PREFIX + "phone.gift.share.preReward";
    // /**
    // * 商户线下活动消息队列路由
    // */
    // String USER_PHONE_REGISTER_ROUTING_KEY_MERCHANT_ACTIVITY = QUEUE_PREFIX + "phone.registered.merchantActivity";

    /**
     * 礼物商城 分享发放奖励队列
     */
    String USER_PHONE_REGISTER_QUEUE_SEND_SHOPGIFT_SHARE = QUEUE_PREFIX + "queue.phone.registered.sendShopGiftShare";

    /**
     * 礼物分享 预发放礼物队列
     */
    String USER_PHONE_GIFT_SHARE_PRE_REWARD_QUEUE = QUEUE_PREFIX + "queue.phone.gift.share.preReward";
    /**
     * 第三方用户解绑集分社队列
     */
    String USER_3RD_UNBIND_QUEUE = "queue.user3rd.unbind";

    /**
     * 商户线下活动消息队列 (旧版, 良品铺子活动结束后就可以作废)
     */
    String USER_PHONE_REGISTER_QUEUE_MERCHANT_ACTIVITY = QUEUE_PREFIX + "queue.phone.registered.merchantActivity";

    /**
     * 商户邀请用户渠道消息队列 (新版)
     */
    String USER_PHONE_REGISTER_QUEUE_MERCHANT_INVITE = QUEUE_PREFIX + "queue.phone.registered.merchantInvite";

    /**
     * 高交会活动 给新注册用户发放礼物消息队列
     */
    String USER_PHONE_REGISTER_QUEUE_SEND_CHTF_GIFT = QUEUE_PREFIX + "queue.phone.registered.sendChtfGift";

    /**
     * 手机号预注册队列
     */
    String USER_PHONE_PRE_REGISTE_QUEUE = QUEUE_PREFIX + "queue.phone.pre-registe";

    /**
     * 管理员发放粮票 给用户发放粮票消息队列
     */
    String USER_PHONE_REGISTER_QUEUE_SEND_FINANCE = QUEUE_PREFIX + "queue.phone.registered.sendFinance";

    /**
     * 管理员发放免拆礼物 给用户发放免拆礼物消息队列
     */
    String USER_PHONE_REGISTER_QUEUE_SEND_EXEMPT_GIFT = QUEUE_PREFIX + "queue.phone.registered.sendExemptGift";

    /**
     * 老用户领取活动奖品消息队列
     */
    String ACTIVITY_RECEIVE_REWARD_QUEUE = QUEUE_PREFIX + "queue.activity.receive.reward";

    /**
     * 新用户发放活动奖品消息队列
     */
    String USER_PHONE_REGISTER_SEND_ACTIVITY_REWARD_QUEUE = QUEUE_PREFIX + "queue.phone.registered.sendActivityReward";

    /**
     * 拆话费收礼人消息队列
     */
    String TELE_DISCHARGE_SEND_GIFT_IDUSER = QUEUE_PREFIX + "queue.teleDischarge.sendGift.user";

    /**
     * 用户关注消息队列
     */
    String USER_ATTENTION_QUEUE = QUEUE_PREFIX + "queue.user-attention";

    /**
     * 初始化资料任务队列
     */
    String USER_PHONE_REGISTER_QUEUE_INIT_INFO_TASK = QUEUE_PREFIX + "queue.phone.registered.initTask";

    /**
     * 完成资料任务队列
     */
    String COMPLETE_INFO_TASK_QUEUE = QUEUE_PREFIX + "queue.complete.info.task";

    /**
     * 查询七牛文件信息队列
     */
    String QINIU_FILE_INFO_QUEUE = QUEUE_PREFIX + "queue.qiniu.file.info";

    /**
     * 动态文件信息回调队列
     */
    String DYNAMIC_FILE_INFO_CALLBACK_QUEUE = QUEUE_PREFIX + "queue.dynamic.file.info";

    /**
     * 广告文件信息回调队列
     */
    String AD_FILE_INFO_CALLBACK_QUEUE = QUEUE_PREFIX + "queue.ad.file.info";

    /**
     * APP 用户锁定队列
     */
    String APP_USER_LOCK_QUEUE = QUEUE_PREFIX + "queue.app.user.lock";

    /**
     * 队列: 销毁token(数据库层面)
     */
    String TOKEN_DESTROY_DATABASE_QUEUE = QUEUE_PREFIX + "queue.token.destroy.database";

    /**
     * 队列: 销毁token(API网关)
     */
    String TOKEN_DESTROY_API_GATE_QUEUE = QUEUE_PREFIX + "queue.token.destroy.apigate";

    /**
     * 队列: 保存VPN
     */
    String CHINAUNICOM_VPN_DIRECT_QUEUE = QUEUE_PREFIX + "queue.chinaunicom.vpn.direct";

    /**
     * 队列: 开通定向流量账号
     */
    String DIRECT_FLOW_OPEN_ACCOUNT = QUEUE_PREFIX + "queue.direct_flow.openAccount";

    /**
     * 队列: 手机号注册成功后，处理定向流量预开通账号
     */
    String DIRECT_FLOW_PRE_OPEN_ACCOUNT_ON_PHONE_REGISTED = QUEUE_PREFIX + "queue.direct_flow.preAccountHandle";

    /**
     * O2O商户已创建: 订单服务
     */
    String O2O_MERCHANT_CREATED_IN_ORDER_MS = QUEUE_PREFIX + "queue.o2o_merchant.created.init_in_order_ms";
    
    /**
     * O2O商户已创建: 商户服务
     */
    String O2O_MERCHANT_CREATED_IN_MERCHANT_MS = QUEUE_PREFIX + "queue.o2o_merchant.created.init_in_merchant_ms";

    /**
     * 品牌已创建: 订单服务
     */
    String BRAND_CREATED_IN_ORDER_MS = QUEUE_PREFIX + "queue.brand.created.init_in_order_ms";
    
    /**
     * 品牌已创建: 商户服务
     */
    String BRAND_CREATED_IN_MERCHANT_MS = QUEUE_PREFIX + "queue.brand.created.init_in_merchant_ms";
    
    /**
     * 下架O2O商户相关商品
     */
    String O2O_MERCHANT_GOODS_TO_OFFSHELF = QUEUE_PREFIX + "queue.o2o_merchant.goods_to_offshelf";
    
    /**
     * O2O商户会员转为品牌会员
     */
    String O2O_MERCHANT_MEMBER_TO_BRAND_MEMBER = QUEUE_PREFIX + "queue.o2o_merchant.member_to_brand_member"; 
    
    /**
     * O2O商户会员积分转为品牌会员积分
     */
    String O2O_MERCHANT_MEMBER_JF_TO_BRAND_MEMBER_JF = QUEUE_PREFIX + "queue.o2o_merchant.member_jf_to_brand_member_jf";
    
    /**
     * O2O商户会员累计消费人民币转为品牌会员累计消费人民币
     */
    String O2O_MERCHANT_MEMBER_CONSUME_RMB_TO_BRAND_MEMBER_CONSUME_RMB = QUEUE_PREFIX + "queue.o2o_merchant.member_consume_rmb_to_brand_member_consume_rmb"; 
}
