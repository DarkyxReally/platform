package com.platform.rabbitmq.utils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ErrorHandler;

import com.platform.rabbitmq.constant.RedisKeyConstant;
import com.platform.rabbitmq.exception.MQListenerExecutionFailedException;

/**
 * MQ异常处理
 * @version: 1.0
 */
@Slf4j
public class MQErrorHandler implements ErrorHandler {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    private MessageConverter msgConverter;

    @Override
    public void handleError(Throwable cause){
        if (cause instanceof MQListenerExecutionFailedException){
            Message mqMsg = ((MQListenerExecutionFailedException)cause).getMqMsg();
            try{
                Object msgObj = msgConverter.fromMessage(mqMsg);
                log.error("handle MQ msg: " + msgObj + " failed, record it to redis.", cause);
                BoundZSetOperations<String,String> zSetOps = stringRedisTemplate.boundZSetOps(RedisKeyConstant.MQ_MSG_ERR_RECORD_KEY);
                zSetOps.add(msgObj.toString(), new Double(System.currentTimeMillis()));
            }catch(Exception e){
                log.error("An error occurred.", cause);
            }
        }
        else{
            log.error("An error occurred.", cause);
        }
    }

}
