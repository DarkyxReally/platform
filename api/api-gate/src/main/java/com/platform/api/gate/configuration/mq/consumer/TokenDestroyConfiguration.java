package com.platform.api.gate.configuration.mq.consumer;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.api.gate.listener.TokenDestroyListener;
import com.platform.rabbitmq.constant.ExchangeConstant;
import com.platform.rabbitmq.constant.QueueConstant;
import com.platform.rabbitmq.constant.RoutingKeyConstant;


/**
 * TOKEN销毁配置
 * @version: 1.0
 */
@Configuration
public class TokenDestroyConfiguration {
    
    /**
     * API网关token销毁队列
     */
    @Autowired
    @Qualifier(QueueConstant.TOKEN_DESTROY_API_GATE_QUEUE)
    private Queue apiGateTokenDestroyQueue;
    
    /**
     * 消息交换机
     */
    @Autowired
    @Qualifier(ExchangeConstant.EXCHANGE_LIGUJOY_DIRECT)
    private DirectExchange directExchange;

    /**
     * API网关token销毁队列绑定   
     * @param directExchange 下线通知的交换机
     * @return
     */
    @Bean
    public Binding apiGateTokenDestroyQueueBinding(){
        return BindingBuilder.bind(apiGateTokenDestroyQueue).to(directExchange).with(RoutingKeyConstant.TOKEN_DESTROY_ROUTING_KEY);
    }
    
    /**
     * token销毁监听
     * @return
     */
    @Bean
    public TokenDestroyListener tokenDestroyListener(){
        return new TokenDestroyListener();
    }

    /**
     * 消息监听容器
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer tokenDestroyListenerContainer(ConnectionFactory connectionFactory, SimpleMessageConverter simpleMessageConverter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(apiGateTokenDestroyQueue);
        container.setMessageConverter(simpleMessageConverter);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        // 设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(tokenDestroyListener());
        return container;
    }
}
