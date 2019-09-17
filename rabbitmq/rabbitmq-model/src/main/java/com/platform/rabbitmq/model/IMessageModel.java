package com.platform.rabbitmq.model;

import java.util.List;
import java.util.Set;

/**
 * 消息推送模型
 * @version: 1.0
 */
public interface IMessageModel {
    
    /**
     * 推送的平台
     * @return
     */
    Set<Platform> platform();

    
    /**
     * 推送的目标用户类型
     * @return
     */
    TargetUserType targetUserType();
    
    /**
     * 目标用户id
     * @return
     */
    List<String> targetUsers();


    /**
     * 标题
     * @return
     */
    String title();

    
    /**
     * 内容
     * @return
     */
    String content();
    
    /**
     * 推送渠道
     * @return
     */
    PushChannel[] channel();
}
