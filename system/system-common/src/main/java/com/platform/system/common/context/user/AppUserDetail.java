package com.platform.system.common.context.user;

import com.platform.system.common.context.user.AuthUserTypeConstant.AuthUserType;

import lombok.Getter;
import lombok.Setter;


/**
 * APP用户详情
 * 
 * @version: 1.0
 */
@Getter
@Setter
public class AppUserDetail extends UserDetail {

    private static final long serialVersionUID = -939807244809875364L;

    @Override
    public AuthUserType userType(){
        return AuthUserType.APP_USER;
    }
    
    /**
     * tokenId
     */
    private String idToken;
}
