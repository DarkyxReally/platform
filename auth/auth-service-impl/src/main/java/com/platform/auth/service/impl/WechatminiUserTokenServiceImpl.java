package com.platform.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.auth.common.config.AuthUserConfig;


/**
 * API网关Token管理
 * @version: 1.0
 */
@Service(WechatminiUserTokenServiceImpl.BEAN_NAME)
public class WechatminiUserTokenServiceImpl extends BaseTokenServiceImpl {

    public static final String BEAN_NAME = "wechatminiUserTokenServiceImpl";
    
    @Autowired
    private AuthUserConfig userConfig;

    @Override
    protected int ttl(){
        return userConfig.getUserTokenExpire();
    }

    @Override
    protected byte[] getPrivateKeyByte(){
        return userConfig.getPrivateKey();
    }

    @Override
    protected byte[] getPublicKeyByte(){
        return userConfig.getPublicKey();
    }
}
