package com.platform.rabbitmq.constant;


/**
 * rabbitmq bean常量
 * @version: 1.0
 */
public class RabbitBeanConstant {

    /**
     * 消息通知模板
     */
    public static final String MESSAGE_NOTICE_TEMPLATE = "messageNoticeTemplate";

    /**
     * 角色添加模板
     */
    public static final String ROLE_ADD_TEMPLATE = "roleAddTemplate";
    
    /**
     * 消息通知监听容器
     */
    public static final String MESSAGE_NOTICE_LISTENER_CONTAINER = "messageNoticeListenerContainer";

    /**
     * 角色添加监听容器
     */
    public static final String ROLE_ADD_LISTENER_CONTAINER = "roleAddListenerContainer";
    
    /**
     * 抢兑监听容器
     */
    public static final String SEXCHANGE_LISTENER_CONTAINER = "sexchangeListenerContainer";
    
    /**
     * 监听器容器工厂类
     */
    public static final String RABBIT_LISTENER_CONTAINER_FACTORY = "rabbitListenerContainerFactory";
}
