package com.platform.auth.service;

import com.platform.auth.model.innermodel.token.AccessToken;
import com.platform.auth.model.model.TokenUserInfo;

/**
 * token管理
 * @version: 1.0
 */
public interface IAccessTokenService {
    
    /**
     * 生成accessToken
     * @param userInfo 用户信息
     * @param userType 用户类型
     * @return
     * @throws Exception
     */
    AccessToken createAccessToken(TokenUserInfo userInfo) throws Exception;
    
    /**
     * 解析tokenInfo
     * @param accessToken
     * @return
     * @throws Exception
     */
    TokenUserInfo getAccessTokenInfo(String accessToken) throws Exception;
    
    /**
     * 注销accessToken, 使token失效
     * @param token
     */
    void invalidAccessToken(String accessToken) throws Exception;

    
}
