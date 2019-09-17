package com.platform.system.common.enums.rest;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.platform.system.common.rest.RestStatus;

/**
 * @version: 1.0
 */
public enum VersionCode implements RestStatus {
    
    /**
     * 当前已是最新版本
     */
    CURR_IS_LAST(43800, "当前已是最新版本"),
    /**
     * 版本号无效
     */
    ERROR_VERSION(43801, "版本号无效")
    ;

    private final int code;

    private final String message;
    
    private final boolean isLogStack;
    
    private static final ImmutableMap<Integer, VersionCode> CACHE;

    static {
        List<Integer> codeList = new ArrayList<Integer>();
        for (VersionCode statusCode : values()) {
            //防止code重复引起歧义
            if (codeList.contains(statusCode.code())){
                throw new IllegalStateException("statuscode重复>>" + statusCode);
            }
            codeList.add(statusCode.code());
        }
        
        final ImmutableMap.Builder<Integer, VersionCode> builder = ImmutableMap.builder();
        for (VersionCode statusCode : values()) {
            builder.put(statusCode.code(), statusCode);
        }
        CACHE = builder.build();
    }

    VersionCode(int code, String message) {
        this.code = code;
        this.message = message;
        this.isLogStack = false;
    }
    
    VersionCode(int code, String message, boolean isLogStack) {
        this.code = code;
        this.message = message;
        this.isLogStack = isLogStack;
    }

    /**
     * 根据code获取枚举
     * @param code
     * @return
     */
    public static VersionCode valueOfCode(int code) {
        final VersionCode status = CACHE.get(code);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }
        return status;
    }

    @Override
    public int code() {
        return code;
    }
    
    @Override
    public String message() {
        return message;
    }

    @Override
    public boolean isLogErrorStack(){
        return isLogStack;
    }
}
