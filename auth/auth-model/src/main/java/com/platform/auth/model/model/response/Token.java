package com.platform.auth.model.model.response;

import java.util.Date;

public class Token {

    /**
     * token
     */
    private String accessToken;

    /**
     * 过期时间
     */
    private Date expire;

    public Token() {
        super();
    }
    
    public Token(String accessToken) {
        super();
        this.accessToken = accessToken;
    }

    public Token(String accessToken, Date expire) {
        super();
        this.accessToken = accessToken;
        this.expire = expire;
    }

    public String getAccessToken(){
        return accessToken;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public Date getExpire(){
        return expire;
    }

    public void setExpire(Date expire){
        this.expire = expire;
    }

}
