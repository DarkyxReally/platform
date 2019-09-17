package com.platform.auth.common.constants;

/**
 * 认证常量
 * @version: 1.0
 */
public class AuthConstants {

    /**
     * jwtid
     */
    public static final String JWT_ID = "id";
    
    /**
     * jwt名称
     */
    public static final String JWT_UNIQUE_NAME = "uniqueName";
    
    /**
     * 用户昵称
     */
    public static final String JWT_USER_NICK_NAME = "name";
    
    /**
     * token类型
     */
    public static final String TOKEN_TYPE = "tokenType";
    
    /**
     * token类型: 后台管理员
     */
    public static final String TOKEN_TYPE_ADMIN = "1";
    /**
     * token类型: 普通用户
     */
    public static final String TOKEN_TYPE_NORMAL_USER = "2";
    /**
     * token类型: 商户
     */
    public static final String TOKEN_TYPE_MERCHANT = "3";
}
