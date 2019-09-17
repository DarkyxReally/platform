package com.platform.auth.model.model.provider.response;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * token信息
 * @version: 1.0
 */
@Getter
@Setter
public class TokenInfoResponse implements Serializable {

    private static final long serialVersionUID = 6539654998256816104L;
    
    /**
     * tokenId
     */
    private String idToken;
    
    /**
     * 用户类型
     */
    private String userType;
    
    /**
     * 过期时间
     */
    private Date expireDate;
    
    /**
     * 用户信息
     */
    private String userJsonInfo;
    
    /**
     * 是否已过期
     * @version: 1.0
     * @return
     */
    public boolean isExpired(){
        if (null == expireDate){
            return true;
        }
        return System.currentTimeMillis() > expireDate.getTime();
    }
}
