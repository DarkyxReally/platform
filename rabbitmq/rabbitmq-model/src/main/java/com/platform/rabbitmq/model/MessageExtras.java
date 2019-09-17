package com.platform.rabbitmq.model;


import com.platform.rabbitmq.model.notice.IExtrasable;

import lombok.Getter;
import lombok.Setter;


/**
 * 消息透传数据
 * @version: 1.0
 */
@Getter
@Setter
public abstract class MessageExtras<T extends IExtrasable> {

    /**
     * 详情数据
     */
    private T detail;
    
    /**
     * 场景
     */
    private String sence;
    
    /**
     * 内容类型,0直显型,1链接型
     */
    private String contentType;
    
    /**
     * 类型为链接型时的跳转地址
     */
    private String contentUrl;
    
}
