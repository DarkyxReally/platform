package com.platform.auth.model.model;

import java.io.Serializable;
import java.util.Date;

import com.platform.auth.model.innermodel.TerminalChannelConstant.TerminalChannel;
import com.platform.auth.model.model.constant.UserTypeConstant.UserType;

import lombok.Getter;
import lombok.Setter;



/**
 * token用户信息
 * @version: 1.0
 */
@Getter
@Setter
public class TokenUserInfo implements Serializable {

    private static final long serialVersionUID = 6539654998256816104L;
    
    /**
     * tokenId
     */
    private String idToken;
    
    /**
     * 用户id
     */
    private String idUser;

    /**
     * 终端渠道(1:APP 2:微信小程序 3:WEB)
     */
    private TerminalChannel terminalChannel;
    
    /**
     * 用户类型
     */
    private UserType userType;
    
    /**
     * 过期时间
     */
    private Date expired;
    
    /**
     * 登录平台
     */
    private String platform;
}
