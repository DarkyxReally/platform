package com.platform.system.common.context.user;

import com.platform.system.common.context.user.AuthUserTypeConstant.AuthUserType;

/**
 * V1.0版本用户信息
 * @version: 1.0
 */
public class V0UserDetail extends UserDetail {

    /** 
    * @fields serialVersionUID : 
    */ 
    private static final long serialVersionUID = 2415026021954003944L;

    @Override
    public AuthUserType userType(){
        return null;
    }

}
