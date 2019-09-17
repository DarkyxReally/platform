package com.platform.auth.service;

import com.platform.auth.model.model.response.Token;

/**
 * 认证服务接口
 * @version: 1.0
 */
public interface AuthService {

    /**
     * 根据账号和密码认证
     * @param username
     * @param password
     * @return 认证成功的token
     * @throws Exception
     */
    Token login(String username, String password) throws Exception;
    
    /**
     * 根据用户名生成token
     * @param idUser
     * @return
     */
    Token createTokenByIdUser(String idUser) throws Exception;
    /**
     * 刷新token
     * 原token失效, 生成新的token
     * @param oldToken
     * @return
     */
    Token refresh(String oldToken) throws Exception;

    /**
     * 验证token
     * @param token
     * @throws Exception
     */
    boolean validate(String token) throws Exception;

    /**
     * 使token失效
     * @param token
     * @return
     */
    Boolean invalid(String token);
}
