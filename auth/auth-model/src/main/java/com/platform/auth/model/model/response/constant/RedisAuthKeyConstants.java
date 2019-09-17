package com.platform.auth.model.model.response.constant;

/**
 * 认证相关redis常量
 * @version: 1.0
 */
public class RedisAuthKeyConstants {

    /**
     * token:管理员用户ID关系
     */
    public static final String TOKEN_ADMINUSERID_KEY_ = "TOKEN:AMINUSERID:";
    
    /**
     * token:app用户ID关系
     */
    public static final String TOKEN_APPUSERID_KEY_ = "TOKEN:APPUSERID:";
    
    /**
     * token:商户用户ID关系
     */
    public static final String TOKEN_MERCHANTUSERID_KEY_ = "TOKEN:MERCHANTUSERID:";
    
    /**
     * token:客户端关系
     */
    public static final String TOKEN_CLIENTID_KEY_ = "TOKEN:CLIENTID:";
}
