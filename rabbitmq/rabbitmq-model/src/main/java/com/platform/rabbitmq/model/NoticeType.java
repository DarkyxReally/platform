package com.platform.rabbitmq.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;
/**
 * 消息类型
 * 
 * 公告10000
 * 系统通知10010
 * 流量通知10020
 * 互动提醒10030
 * 下线通知10040
 * APP推送10050
 * 
 * @version: 1.0
 */
public enum NoticeType {
    /** 公告 */
    ANNOUNCEMENT(10000),
    /** 系统通知 */
    SYSTEM_NOTICE(10010),
    /** 流量通知 */
    RATE_NOTICE(10020),
    /** 互动提醒 */
    INTERACT_REMIND(10030), 
    /** 下线通知 */
    LOG_OUT(10040),
    /** APP通知 */
    APP_NOTICE(10050),
    /** 内容通知 */
    CONTENT_NOTICE(10060);

    private final int code;

    NoticeType(int code) {
        this.code = code;
    }

    private static final ImmutableMap<Integer, NoticeType> CACHE;

    static{
        List<Integer> codeList = new ArrayList<Integer>();
        for(NoticeType type : values()){
            // 防止code重复引起歧义
            if(codeList.contains(type.code())){
                throw new IllegalStateException("statuscode重复>>" + type);
            }else{
                codeList.add(type.code());
            }
        }

        final ImmutableMap.Builder<Integer, NoticeType> builder = ImmutableMap.builder();
        for(NoticeType type : values()){
            builder.put(type.code(), type);
        }
        CACHE = builder.build();
    }

    public int code(){
        return code;
    }

    /**
     * 根据code获取枚举
     * @param code
     * @return
     */
    public static NoticeType valueOfCode(int code){
        final NoticeType type = CACHE.get(code);
        if(type == null){
            // code 不正确
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }
        return type;
    }
}
