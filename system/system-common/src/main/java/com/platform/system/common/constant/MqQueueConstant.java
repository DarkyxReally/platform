package com.platform.system.common.constant;

/**
 * MQ队列常量
 * @version: 1.0
 */
public final class MqQueueConstant {
    
    /**
     * 队列前缀
     */
    private static final String QUEUE_PREFIX = "platformapp.";

    /**
     * 交换机: 消息推送
     */
    public static final String EXCHANGE_MESSAGE_NOTICE = QUEUE_PREFIX + "exchange.messageNotice";
    
    /**
     * 队列: 消息推送(APP推送)
     */
    public static final String QUEUE_MESSAGE_NOTICE = QUEUE_PREFIX + "queue.messageNotice";
    
    /**
     * 路由: 消息推送(APP推送)
     */
    public static final String ROUTINGKEY_MESSAGE_NOTICE = QUEUE_PREFIX + "routingkey.messageNotice";
}
