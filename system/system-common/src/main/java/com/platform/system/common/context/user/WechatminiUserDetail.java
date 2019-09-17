package com.platform.system.common.context.user;

import com.platform.system.common.context.user.AuthUserTypeConstant.AuthUserType;

import lombok.Getter;
import lombok.Setter;


/**
 * 小程序用户详情
 * 
 * @version: 1.0
 */
@Getter
@Setter
public class WechatminiUserDetail extends UserDetail {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8759716813747614686L;

	@Override
    public AuthUserType userType(){
        return AuthUserType.APP_USER;
    }
    
    /**
     * tokenId
     */
    private String idToken;
}
