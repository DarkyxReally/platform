package com.platform.auth.model.model.api.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatRegisterResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2304216631244997682L;
	
	/**
     * token信息
     */
    private String token;
    
    /** 用户id */
    private String userId;
}
