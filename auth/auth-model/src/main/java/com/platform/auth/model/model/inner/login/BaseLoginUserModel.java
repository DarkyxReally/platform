package com.platform.auth.model.model.inner.login;

import java.util.Date;

import com.platform.auth.model.enums.login.ILoginSence;
import com.platform.auth.model.innermodel.TerminalChannelConstant.TerminalChannel;
import com.platform.auth.model.model.inner.UserEnvironment;

import lombok.Getter;
import lombok.Setter;


/**
 * 用户登录信息基类
 * @version: 1.0
 */
@Getter
@Setter
public abstract class BaseLoginUserModel implements IUserLoginModel{

    /**
     * 登陆场景
     */
    private ILoginSence loginSence;
    
    /**
     * 终端渠道
     */
    private TerminalChannel channel;
    
    /**
     * 用户环境信息
     */
    private UserEnvironment environment;
    
    /**
     * 用户id
     */
    private String idUser;
    
    /**
     * 昵称
     */
    private String nickName;
    
    /**
     * 登陆时间
     */
    private Date loginDate;
}
