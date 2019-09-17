package com.platform.auth.model.model.inner.login;



import com.platform.auth.model.model.constant.UserTypeConstant.UserType;

import lombok.Getter;
import lombok.Setter;


/**
 * 小程序用户登录模型
 * @version: 1.0
 */
@Getter
@Setter
public class WechatminiUserLoginModel extends BaseLoginUserModel{

    @Override
    public UserType userType(){
        return UserType.WECHATMINI_USER;
    }
}
