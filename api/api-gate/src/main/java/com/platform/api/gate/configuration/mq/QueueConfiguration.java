package com.platform.api.gate.configuration.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.platform.rabbitmq.constant.QueueConstant;


/**
 * Rabbit 队列申明配置
 * @version: 1.0
 */
@Configuration
public class QueueConfiguration {

    /**
     * API网关销毁token队列
     * @return
     */
    @Bean(QueueConstant.TOKEN_DESTROY_API_GATE_QUEUE)
    public Queue apiGateTokenDestroyQueue(){
        // 用队列持久
        return new Queue(QueueConstant.TOKEN_DESTROY_API_GATE_QUEUE, true);
    }
  
}
