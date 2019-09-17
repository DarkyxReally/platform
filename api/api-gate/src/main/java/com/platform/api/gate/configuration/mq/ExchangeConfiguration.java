package com.platform.api.gate.configuration.mq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.rabbitmq.constant.ExchangeConstant;
import com.platform.system.common.constant.MqQueueConstant;


/**
 * Rabbit 交换机配置
 * @version: 1.0
 */
@Configuration
public class ExchangeConfiguration {
    
    /**
     * Direct交换机  
     * @return
     */
    @Bean(ExchangeConstant.EXCHANGE_LIGUJOY_DIRECT)
    public DirectExchange directExchange(){
        // DirectExchange:按照routingkey分发到指定队列
        // TopicExchange:多关键字匹配
        // FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
        // HeadersExchange ：通过添加属性key-value匹配
        return new DirectExchange(ExchangeConstant.EXCHANGE_LIGUJOY_DIRECT);
    }
    
    /**
     * 交换机(消息通知)  
     * @return
     */
    @Deprecated
    @Bean(MqQueueConstant.EXCHANGE_MESSAGE_NOTICE)
    public DirectExchange messageNoticeExchange(){
        // DirectExchange:按照routingkey分发到指定队列
        // TopicExchange:多关键字匹配
        // FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
        // HeadersExchange ：通过添加属性key-value匹配
        return new DirectExchange(MqQueueConstant.EXCHANGE_MESSAGE_NOTICE);
    }
}
