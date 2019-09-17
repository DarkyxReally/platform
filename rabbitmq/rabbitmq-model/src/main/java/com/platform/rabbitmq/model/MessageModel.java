package com.platform.rabbitmq.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息模型
 * @version: 1.0
 */
@Getter
@Setter
public class MessageModel implements IMessageModel{
	
    /**
     * 透传的数据
     */
    private Map<String, String> extras;
    
    /**
     * 推送渠道
     */
	private PushChannel[] channels;
	
    /**
     * 推送的平台
     */
    private Set<Platform> platform;
    
    /**
     * 目标用户类型
     */
    private TargetUserType targetUserType;
    
    /**
     * 目标用户
     */
    private List<String> targetUsers;
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 内容
     */
    private String content;

    /**
     * 添加透传数据
     * @param key
     * @param value
     */
    public void addExtra(String key, String value){
        Objects.requireNonNull(key, value);
        if (extras == null){
            extras = new HashMap<String, String>();
        }
        extras.put(key, value);
    }

    
    public Map<String, String> getExtras(){
        return extras;
    }

    public void setExtras(Map<String, String> extras){
        this.extras = extras;
    }
    @Override
    public Set<Platform> platform(){
        return getPlatform();
    }

    @Override
    public TargetUserType targetUserType(){
        return getTargetUserType();
    }

    @Override
    public List<String> targetUsers(){
        return getTargetUsers();
    }

    @Override
    public String title(){
        return getTitle();
    }

    @Override
    public String content(){
        return getContent();
    }

	@Override
	public PushChannel[] channel() {
		return getChannels();
	}
}
