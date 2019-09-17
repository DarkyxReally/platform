package com.platform.rabbitmq.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;

/**
 * 平台
 * @version: 1.0
 */
public enum Platform {
    ALL(0), ANDROID(1), IOS(2);

    private final int code;

    Platform(int code) {
        this.code = code;
    }

    private static final ImmutableMap<Integer, Platform> CACHE;

    static{
        List<Integer> codeList = new ArrayList<Integer>();
        for(Platform platform : values()){
            // 防止code重复引起歧义
            if(codeList.contains(platform.code())){
                throw new IllegalStateException("statuscode重复>>" + platform);
            }else{
                codeList.add(platform.code());
            }
        }

        final ImmutableMap.Builder<Integer, Platform> builder = ImmutableMap.builder();
        for(Platform platform : values()){
            builder.put(platform.code(), platform);
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
    public static Platform valueOfCode(int code){
        final Platform platform = CACHE.get(code);
        if(platform == null){
            // code 不正确
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }
        return platform;
    }

}
