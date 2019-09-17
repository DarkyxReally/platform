package com.platform.rabbitmq.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.rabbitmq.config.RabbitMQConfig;
import com.platform.rabbitmq.constant.QueueConstant;
import com.platform.rabbitmq.constant.RabbitBeanConstant;


/**
 * Rabbit配置
 * @version: 1.0
 */
@Configuration
public class RabbitMqConfiguration {

    @Autowired
    private RabbitMQConfig rabbitmqConfig;
    
    /**
     * 消息转换器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    
    /**
     * 消息转换
     */
    @Bean
    public SimpleMessageConverter simpleMessageConverter(){
        return new SimpleMessageConverter();
    }

    /**
     * rabbitmq链接工厂
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(rabbitmqConfig.getHost() + ":" + rabbitmqConfig.getPort());
        connectionFactory.setUsername(rabbitmqConfig.getUserName());
        connectionFactory.setPassword(rabbitmqConfig.getPassword());
        connectionFactory.setVirtualHost(rabbitmqConfig.getVirtualHost());
        connectionFactory.setPublisherConfirms(true); // 必须要设置 才能进行消息的回调
        return connectionFactory;
    }

    /**
     * 监听器容器工厂
     * @param connectionFactory
     * @return
     */
    @Bean(RabbitBeanConstant.RABBIT_LISTENER_CONTAINER_FACTORY)
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter());
        return factory;
    }

    /**
     * 消息推送交换机类型
     * @return
     */
    @Bean(QueueConstant.MESSAGE_NOTICE_EXCHANGE)
    public DirectExchange messageNoticeExchange(){
        // DirectExchange:按照routingkey分发到指定队列
        // TopicExchange:多关键字匹配
        // FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
        // HeadersExchange ：通过添加属性key-value匹配
        return new DirectExchange(QueueConstant.MESSAGE_NOTICE_EXCHANGE);
    }

    /**
     * 消息通知队列
     * @return
     */
    @Bean(QueueConstant.MESSAGE_NOTICE_QUEUE)
    public Queue messageNoticeQueue(){
        // 队列持久
        Map<String, Object> args = new HashMap<String, Object>();
        // 存活时间最大为 180 秒
        args.put("x-message-ttl", 180000);
        return new Queue(QueueConstant.MESSAGE_NOTICE_QUEUE, true, false, false, args);
    }

    /**
     * 将消息通知队列绑定到交换机   
     * @return
     */
    @Bean
    public Binding messageNoticeBinding(){
        // 将消息通知队列绑定到交换机
        return BindingBuilder.bind(messageNoticeQueue()).to(messageNoticeExchange()).with(QueueConstant.MESSAGE_NOTICE_ROUTINGKEY);
    }
    
//    /**
//     * 消息通知监听
//     * @return
//     */
//    @Bean
//    public MessageNoticeListener messageNoticeListener(){
//        return new MessageNoticeListener();
//    }

    /**
     * 消息监听容器
     * @return
     */
    @Bean(RabbitBeanConstant.MESSAGE_NOTICE_LISTENER_CONTAINER)
    public SimpleMessageListenerContainer messageContainer(){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(messageNoticeQueue());
        container.setMessageConverter(simpleMessageConverter());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        // 设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener(messageNoticeListener());
        return container;
    }

}
