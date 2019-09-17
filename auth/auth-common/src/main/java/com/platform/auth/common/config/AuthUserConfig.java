package com.platform.auth.common.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * 用户认证配置
 * auth: 
 *   user: #用户认证
 *     token-header: 请求头字段
 *     rsa-secret: token加密密钥
 *     expire: 
 *        normal: 2592000 #普通用户token有效期
 *        admin: 2592000 #管理员用户token有效期
 * @version: 1.0
 */
public class AuthUserConfig implements AuthConfig {

    /**
     * token头字段
     */
    @Value("${auth.user.token-header:Authorization}")
    private String tokenHeader;

    /**
     * 加密密钥
     */
    @Value("${auth.user.rsa-secret:null}")
    private String rsaSecret;

    /**
     * app用户token过期时间
     * 默认3600秒
     */
    @Value("${auth.user.expire.normal:3600}")
    private int userTokenExpire;

    /**
     * 验证token的公钥
     */
    private byte[] publicKey;

    /**
     * 加密token的私钥
     */
    private byte[] privateKey;

    @Override
    public String getTokenHeader(){
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader){
        this.tokenHeader = tokenHeader;
    }

    public String getRsaSecret(){
        return "null".equals(rsaSecret) ? null : rsaSecret;
    }

    public void setRsaSecret(String rsaSecret){
        this.rsaSecret = rsaSecret;
    }

    public int getUserTokenExpire(){
        return userTokenExpire;
    }

    public void setUserTokenExpire(int userTokenExpire){
        this.userTokenExpire = userTokenExpire;
    }

    public byte[] getPublicKey(){
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey){
        this.publicKey = publicKey;
    }

    public byte[] getPrivateKey(){
        return privateKey;
    }

    public void setPrivateKey(byte[] privateKey){
        this.privateKey = privateKey;
    }
}
