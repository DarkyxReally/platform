package com.platform.rabbitmq.service;

import com.platform.rabbitmq.model.IMessageModel;

/**
 * 消息推送接口
 * @version: 1.0
 */
public interface MessagePushService {
    
    /**
     * 消息推送
     * @param messageModel
     */
    void execPush(IMessageModel messageModel);
}
