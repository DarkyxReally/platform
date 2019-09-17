package com.platform.rabbitmq.listener;

/**
 * MQ消息处理结果
 * 
 * @version: 1.0
 */
public enum HandleResult {
    /**
     * 处理成功
     */
    ACCEPT,
    /**
     * 可以重试的错误
     */
    RETRY,
    /**
     * 无需重试的错误
     */
    REJECT
}
