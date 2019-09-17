package com.platform.rabbitmq.exception;

import org.springframework.amqp.core.Message;

/**
 * MQ消息监听执行失败异常
 * @version: 1.0
 */
public class MQListenerExecutionFailedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * MQ 消息
     */
    private Message mqMsg;

    public MQListenerExecutionFailedException() {
        super();
    }

    public MQListenerExecutionFailedException(String msg) {
        super(msg);
    }
    
    public MQListenerExecutionFailedException(Message mqMsg, String message) {
        super(message);
        this.mqMsg = mqMsg;
    }
    
    public MQListenerExecutionFailedException(Message mqMsg, String message, Throwable cause) {
        super(message, cause);
        this.mqMsg = mqMsg;
    }

    public MQListenerExecutionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MQListenerExecutionFailedException(Throwable cause) {
        super(cause);
    }

    public Message getMqMsg(){
        return mqMsg;
    }

    public void setMqMsg(Message mqMsg){
        this.mqMsg = mqMsg;
    }

}
