package com.platform.api.gate.listener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.platform.api.gate.service.TokenServiceImpl;
import com.platform.system.common.model.mq.TokenDestoryModel;
import com.platform.system.common.trace.annotations.Trace;
import com.rabbitmq.client.Channel;

/**
 * token销毁监听
 * @version: 1.0
 */
@Slf4j
public class TokenDestroyListener implements ChannelAwareMessageListener {

    @Autowired
    private TokenServiceImpl tokenService;

    /**
     * 解析消息
     * @return
     */
    private TokenDestoryModel parseMessage(Message message){
        if(null == message.getBody()){ return null; }
        String content;
        try{
            content = new String(message.getBody(), message.getMessageProperties().getContentEncoding());
        }catch(UnsupportedEncodingException e){
            content = new String(message.getBody(), Charset.forName("UTF-8"));
        }
        try{
            log.info("销毁token消息内容:{}", content);
            return JSONObject.parseObject(content, TokenDestoryModel.class);
        }catch(JSONException e){
            log.info("JSON格式不正确:{}", content);
            throw new MessageConversionException("JSON格式不正确");
        }
    }

    /**
     * 校验参数
     */
    private void check(TokenDestoryModel dto){
        if(null == dto){
            // 抛特定异常
            throw new MessageConversionException("消息对象为空");
        }
    }

    @Trace
    @Override
    public void onMessage(Message message, Channel channel) throws Exception{
        try{
            TokenDestoryModel model = parseMessage(message);
            check(model);
            // 销毁用户所有token
            List<String> idUsers = model.getIdUsers();
            if (CollectionUtils.isNotEmpty(idUsers)){
                for(String idUser : idUsers){
                    tokenService.destroyToken(idUser);
                }
            }
            // TODO 销毁指定的token
            // List<String> idTokens = model.getIdTokens();
            
            
            // 销毁用户指定token
            Map<String, List<String>> userTokenMap = model.getUserTokenMap();
            if (null !=  userTokenMap){
                for(Entry<String, List<String>> entry: userTokenMap.entrySet()){
                    tokenService.destroyToken(entry.getKey(), entry.getValue());
                }
            }
            
            confirmMsg(message, channel);
        }catch(MessageConversionException e){
            log.info(e.getMessage());
            rejectMsg(message, channel);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            requeueMsg(message, channel);
        }
    }
    
    /**
     * 重新回到队列
     * 
     */
    private void requeueMsg(Message message, Channel channel) throws IOException,InterruptedException{
        // 强制休眠2000再回到队列,防止出现错误刷屏
        Thread.sleep(2000);
        log.info("消息即将重新返回队列...");
        // requeue为是否重新回到队列
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
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
     * 拒绝消息
     * 
     */
    private void rejectMsg(Message message, Channel channel) throws IOException{
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }
}
