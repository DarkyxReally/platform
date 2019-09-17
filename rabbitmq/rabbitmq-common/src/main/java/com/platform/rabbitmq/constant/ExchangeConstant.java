package com.platform.rabbitmq.constant;

/**
 * 队列常量
 * @version: 1.0
 */
public interface ExchangeConstant extends IRabbitConstant {

    /**
     * 交换机: direct模式消息推送
     * @deprecated 使用EXCHANGE_LIGUJOY_DIRECT替代
     */
    @Deprecated
    String MESSAGE_NOTICE_EXCHANGE = PREFIX + "exchange.messageNotice";
    
    /**
     * 交换机: direct模式消息推送
     */
    String EXCHANGE_LIGUJOY_DIRECT = PREFIX + "exchange.direct";

    /**
     * 交换机: 主题模式推送
     */
    String EXCHANGE_LIGUJOY_TOPIC = PREFIX + "exchange.topic";
    
    /**
     * 延迟交换机
     */
    String DELAYED_TOPIC_EXCHANGE = PREFIX + "delayed.exchange.topic";

    /**
     * 添加角色
     */
    String ROLE_ADD_EXCHANGE = PREFIX + "exchange.roldAdd";

    /**
     * 新手礼交换机
     */
    String NOVICE_EXCHANGE = PREFIX + "exchange.noviceAward";

    /**
     * M金钱包交换机
     */
    String MFINANCE_EXCHANGE = PREFIX + "exchange.mFinance";

}
