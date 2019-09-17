package com.platform.auth.model.innermodel.token;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * AccessToken
 * @version: 1.0
 */
@Getter
@Setter
public class AccessToken implements IToken, Serializable {
    
    private static final long serialVersionUID = 5853272566618841059L;

    /**
     * tokenID
     */
    private String idToken;
    
    /**
     * 平台
     */
    private String platform;

    /**
     * token
     */
    private String token;

    /**
     * 过期时间
     */
    private Date expire;

    @Override
    public String token(){

        return this.token;
    }

    @Override
    public Date expired(){
        return this.expire;
    }
}
