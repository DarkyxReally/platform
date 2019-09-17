package com.platform.system.common.context.user;

import com.platform.system.common.context.user.AuthUserTypeConstant.AuthUserType;

/**
 * 用户
 * @version: 1.0
 */
public interface IUserDetail {

    /**
     * 用户类别
     * @version: 1.0
     * @return
     */
    AuthUserType userType();
    
    /**
     * 用户昵称
     */
    String nickName();
    
    /**
     * 用户id
     * @version: 1.0
     * @return
     */
    String userId();
}
