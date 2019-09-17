package com.platform.rabbitmq.model.notice;


/**
 * 消息透传
 * @version: 1.0
 * @param <T>
 */
public interface IMessageExtras<T extends IExtrasable> {
    
    /**
     * 透传的信息
     */
    T extras();
}
