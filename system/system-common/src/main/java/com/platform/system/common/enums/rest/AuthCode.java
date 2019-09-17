package com.platform.system.common.enums.rest;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.platform.system.common.rest.RestStatus;

/**
 * 认证异常码
 * 403XX 认证相关错误码(服务间认证及用户认证) 
 * @version: 1.0
 */
public enum AuthCode implements RestStatus {
    
    /**
     * token无效(用户token)
     */
    TOKEN_INVALID(40300, "token无效"),
    /**
     * token刷新失败
     */
    TOKEN_REFRESH_FAIL(40301, "刷新失败"),
    /**
     * token注销失败
     */
    TOKEN_INVALID_FAIL(40302, "注销失败"),
    
    /**
     * 无效客户端(服务间验证失败)
     */
    CLIENT_INVALID(40303, "无效客户端"),
    /**
     * 未授权
     */
    NOT_AUTH(40304, "未授权");
    

    private final int code;

    private final String message;
    
    private final boolean isLogStack;
    
    private static final ImmutableMap<Integer, AuthCode> CACHE;

    static {
        List<Integer> codeList = new ArrayList<Integer>();
        for (AuthCode statusCode : values()) {
            //防止code重复引起歧义
            if (codeList.contains(statusCode.code())){
                throw new IllegalStateException("statuscode重复>>" + statusCode);
            }
            codeList.add(statusCode.code());
        }
        
        final ImmutableMap.Builder<Integer, AuthCode> builder = ImmutableMap.builder();
        for (AuthCode statusCode : values()) {
            builder.put(statusCode.code(), statusCode);
        }
        CACHE = builder.build();
    }

    AuthCode(int code, String message) {
        this.code = code;
        this.message = message;
        this.isLogStack = false;
    }
    
    AuthCode(int code, String message, boolean isLogStack) {
        this.code = code;
        this.message = message;
        this.isLogStack = isLogStack;
    }

    /**
     * 根据code获取枚举
     * @param code
     * @return
     */
    public static AuthCode valueOfCode(int code) {
        final AuthCode status = CACHE.get(code);
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
