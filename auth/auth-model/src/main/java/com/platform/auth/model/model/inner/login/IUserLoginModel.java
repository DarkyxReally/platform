package com.platform.auth.model.model.inner.login;

import com.platform.auth.model.model.constant.UserTypeConstant.UserType;

/**
 * 用户登录模型
 * @version: 1.0
 */
public interface IUserLoginModel {

    /**
     * 用户类型
     * 
     * @version: 1.0
     * @return
     */
    abstract UserType userType();
}
