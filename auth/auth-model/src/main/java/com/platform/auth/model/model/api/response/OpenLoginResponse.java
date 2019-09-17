package com.platform.auth.model.model.api.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 第三方开放平台登录结果
 * @version: 1.0
 */
@Getter
@Setter
public class OpenLoginResponse implements Serializable {

    private static final long serialVersionUID = 996803639878504235L;
    
    /** 已注册**/
    public final static String IS_REGIST_YES = "01";
    
    /**未注册**/
    public final static String IS_REGIST_NO = "02";

    /**
     * 第一版返回的token
     * 不要再使用该字段了,后面逐步删除掉该字段
     */
    @Deprecated
    private String token;

    /**
     * token信息
     */
    private AccessTokenResponse accessToken;
    

    /**
     * 是否注册
     * 第一版用于标识用户注册与否的字段
     * 不要再使用该字段了,后面逐步删除掉该字段
     */
    @Deprecated
    private String isregist;
    
    /**
     * 临时token
     */
    private String temp_token;
}
