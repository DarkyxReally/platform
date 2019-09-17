package com.platform.user.innermodel.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6986025792302574929L;
	
	/**
     * 用户id
     */
    private String userId;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 第三方头像
     */
    private String url;
}
