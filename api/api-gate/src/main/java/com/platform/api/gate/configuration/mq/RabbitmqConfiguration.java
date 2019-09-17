package com.platform.api.gate.configuration.mq;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.platform.api.gate.configuration.properties.RabbitMQProperties;


/**
 * Rabbit配置
 * @version: 1.0
 */
@Configuration
public class RabbitmqConfiguration {

    /**
     * MQ配置屬性
     * @return
     */
    @Bean
    public RabbitMQProperties rabbitMQProperties(){
        return new RabbitMQProperties();
    }
    
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
     * @return
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
    public ConnectionFactory connectionFactory(RabbitMQProperties mqProperties){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(mqProperties.getHost() + ":" + mqProperties.getPort());
        connectionFactory.setUsername(mqProperties.getUsername());
        connectionFactory.setPassword(mqProperties.getPassword());
        connectionFactory.setVirtualHost(mqProperties.getVirtualHost());
        connectionFactory.setPublisherConfirms(true); // 必须要设置 才能进行消息的回调
        return connectionFactory;
    }

    /**
     * 监听器容器工厂
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter());
        return factory;
    }
    
    /**
     * rabbitmq模板
     * @return
     */
    @Bean
    // 必须是prototype类型
    // 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate messageNoticeRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(simpleMessageConverter());
        return template;
    }
}
