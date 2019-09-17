package com.platform.rabbitmq.listener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.alibaba.fastjson.JSONObject;
import com.platform.rabbitmq.model.mq.HandleResult;
import com.platform.system.common.trace.annotations.Trace;
import com.rabbitmq.client.Channel;

/**
 * 消息监听基类
 * 
 * @version: 1.0
 */
@Slf4j
public abstract class BaseListener<M> implements ChannelAwareMessageListener {

    /**
     * 默认延迟2000毫秒回到队列
     */
    private static final long DEFAULT_DELAY_REQUEUE_TIME = 2000L;
    
    /**
     * 参数校验
     * @version: 
     * @param model
     */
    protected abstract void paramsCheck(M model);
    
    /**
     * 开始处理
     * 
     * @version: 1.0
     * @param model
     * @return
     */
    protected abstract void doHandle(M model) throws Exception;

    /**
     * 发生异常时, 根据异常判断要返回一个什么的处理结果
     * @version: 
     * @param e
     * @param message
     * @return
     */
    protected abstract HandleResult onException(Exception e, Message message);

    /**
     * 获取模型类型
     * 
     * @version: 1.0
     * @return
     */
    protected abstract Class<M> getMessageModelClass();
    
    /**
     * 成功处理
     * @version: 
     * @param model
     */
    protected void onSuccessHandle(M model){
        
    }
    
    /**
     * 延迟回到队列时间
     * @version: 
     * @return
     */
    protected long delayTimeReturnQueue(){
        return DEFAULT_DELAY_REQUEUE_TIME;
    }

    /**
     * 解析对象
     * 
     * @version: 1.0
     * @param message
     * @return
     */
    protected M parseModel(Message message){
        String body = getMessageBody(message);
        if(null == body){
            return null;
        }
        return JSONObject.parseObject(body, getMessageModelClass());
    }

    /**
     * 获取消息内容
     * 
     * @version:1.0
     * @param message
     * @return
     */
    protected String getMessageBody(Message message){
        if(null == message.getBody()){
            return null;
        }
        try{
            return new String(message.getBody(), message.getMessageProperties().getContentEncoding());
        }catch(UnsupportedEncodingException e){
            return new String(message.getBody(), Charset.forName("UTF-8"));
        }
    }

    @Trace
    @Override
    public final void onMessage(Message message, Channel channel) throws Exception{
        HandleResult action = HandleResult.RETRY;
        try{
            // 如果成功完成则action=Action.ACCEPT
            M model = parseModel(message);
            // 参数校验
            paramsCheck(model);
            // 开始业务处理
            doHandle(model);
            // 没有异常,成功完成
            action = HandleResult.ACCEPT;
            onSuccessHandle(model);
        }catch(Exception e){
            // 根据异常种类决定是ACCEPT、RETRY还是 REJECT
            action = onException(e, message);
        }finally{
            // 通过finally块来保证Ack/Nack会且只会执行一次
            if(action == HandleResult.ACCEPT){
                confirmMsg(message, channel);
            }else if(action == HandleResult.RETRY){
                requeueMsg(message, channel);
            }else{
                rejectMsg(message, channel);
            }
        }
    }

    /**
     * 确认消息
     * 
     */
    private void confirmMsg(Message message, Channel channel) throws IOException{
        // false只确认当前一个消息收到，true确认所有consumer获得的消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 重新回到队列
     * 
     */
    private void requeueMsg(Message message, Channel channel) throws IOException,InterruptedException{
        // 强制休眠,再回到队列,防止出现错误刷屏
        Thread.sleep(delayTimeReturnQueue());
        log.info("消息即将重新返回队列...");
        // requeue为是否重新回到队列
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
    }

    /**
     * 拒绝消息
     * 
     */
    private void rejectMsg(Message message, Channel channel) throws IOException{
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }
}
