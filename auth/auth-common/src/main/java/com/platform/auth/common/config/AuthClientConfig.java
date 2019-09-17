package com.platform.auth.common.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * 客户端加密配置
 * auth: 
 *   client: #客户端服务认证
 *     id: auth-ms
 *     secret: 123456
 *     token-header: client-token
 *     expire: 2592000
 *     rsa-secret: token加密密钥T
 * @version: 1.0
 */
public class AuthClientConfig implements AuthConfig {

    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 客户端id
     */
    @Value("${auth.client.id:null}")
    private String clientId;
    /**
     * 客户端密码
     */
    @Value("${auth.client.secret}")
    private String secret;
    /**
     * 客户端认证请求头
     */
    @Value("${auth.client.token-header:client-token}")
    private String tokenHeader;

    /**
     * token有效期
     * 默认3600秒
     */
    @Value("${auth.client.expire:3600}")
    private int expire;

    /**
     * token加密密钥
     */
    @Value("${auth.client.rsa-secret:null}")
    private String rsaSecret;
    
    @Value("${auth.client.fast-fail:false}")
    private boolean fastFail;

    /**
     * 验证token公钥
     */
    private byte[] publicKey;

    /**
     * 加密token私钥
     */
    private byte[] privateKey;
    
    /**
     * 用于标记是否初始化过
     */
    private boolean init;

    public String getClientId(){
        return "null".equals(clientId) ? applicationName : clientId;
    }

    public void setClientId(String clientId){
        this.clientId = clientId;
    }

    public String getSecret(){
        return secret;
    }

    public void setSecret(String secret){
        this.secret = secret;
    }

    @Override
    public String getTokenHeader(){
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader){
        this.tokenHeader = tokenHeader;
    }

    public int getExpire(){
        return expire;
    }

    public void setExpire(int expire){
        this.expire = expire;
    }

    public String getRsaSecret(){
        return "null".equals(rsaSecret) ? null : rsaSecret;
    }

    public void setRsaSecret(String rsaSecret){
        this.rsaSecret = rsaSecret;
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

    
    public boolean isFastFail(){
        return fastFail;
    }
    
    public void setFastFail(boolean fastFail){
        this.fastFail = fastFail;
    }
    
    public boolean isInit(){
        return init;
    }
    
    public void setInit(boolean init){
        this.init = init;
    }
}
