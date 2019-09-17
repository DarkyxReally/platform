package com.platform.rabbitmq.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;

/**
 * 目标用户类型
 * @version: 1.0
 */
public enum TargetUserType {

    /**
     * 所有用户
     */
    ALL(0),
    /**
     * 指定别名
     */
    BY_ALIAS(1),
    
    /**
     * 根据极光ID推送
     */
    BY_ID(2),
    
    /**
     * 根据Token推送
     */
    BY_TOKEN(3),

    /**
     * 根据tag标签推送
     */
    BY_TAGS(4)
    ;

    private final int code;

    TargetUserType(int code) {
        this.code = code;
    }

    private static final ImmutableMap<Integer, TargetUserType> CACHE;

    static{
        List<Integer> codeList = new ArrayList<Integer>();
        for(TargetUserType type : values()){
            // 防止code重复引起歧义
            if(codeList.contains(type.code())){
                throw new IllegalStateException("statuscode重复>>" + type);
            }else{
                codeList.add(type.code());
            }
        }

        final ImmutableMap.Builder<Integer, TargetUserType> builder = ImmutableMap.builder();
        for(TargetUserType type : values()){
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
    public static TargetUserType valueOfCode(int code){
        final TargetUserType type = CACHE.get(code);
        if(type == null){
            // code 不正确
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }
        return type;
    }
}
