package com.platform.rabbitmq.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;

/**
 * 内容类型
 * @version: 1.0
 */
public enum ContentType {

    /** 直显 */
    SHOW(0),
    /** 链接 */
    LINK(1);
    
    private final int code;

    ContentType(int code) {
        this.code = code;
    }

    private static final ImmutableMap<Integer, ContentType> CACHE;

    static{
        List<Integer> codeList = new ArrayList<Integer>();
        for(ContentType type : values()){
            // 防止code重复引起歧义
            if(codeList.contains(type.code())){
                throw new IllegalStateException("statuscode重复>>" + type);
            }else{
                codeList.add(type.code());
            }
        }

        final ImmutableMap.Builder<Integer, ContentType> builder = ImmutableMap.builder();
        for(ContentType type : values()){
            builder.put(type.code(), type);
        }
        CACHE = builder.build();
    }

    public int code(){
        return code;
    }

    /**
     * 根据code获取枚举
     * @return
     */
    public static ContentType valueOfCode(int code){
        final ContentType type = CACHE.get(code);
        if(type == null){
            // code 不正确
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }
        return type;
    }
}
