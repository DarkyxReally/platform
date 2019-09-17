package com.platform.auth.service.impl.util;

import com.platform.auth.common.util.jwt.IJWTInfo;
import com.platform.auth.common.util.jwt.JWTHelper;
import com.platform.auth.model.model.response.Token;


/**
 * 能缓存的token工具类
 * @version: 1.0
 */
public abstract class CacheableTokenUtil extends BaseTokenUtil{
    
    /**
     * 缓存token
     * @param token
     * @param user
     */
    protected abstract void cacheToken(Token token, IJWTInfo jwtInfo);

    /**
     * token是否存在
     * @param token
     * @return
     */
    protected abstract boolean isTokenExists(String token);

    /**
     * 生成token
     * @param jwtInfo
     * @return
     * @throws Exception
     */
    @Override
    public Token generateToken(IJWTInfo jwtInfo) throws Exception{
        super.generateToken(jwtInfo);
        Token token = generateToken(jwtInfo, getPrivateKeyByte(), getExpireTime());
        // 缓存token信息
        cacheToken(token, jwtInfo);
        return token;
    }

    /**
     * 从token获取信息
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public IJWTInfo getInfoFromToken(String token) throws Exception{
        return JWTHelper.parseToken(token, getPublicKeyByte());
    }
}
