package com.platform.auth.service.impl.util;

import io.jsonwebtoken.SignatureAlgorithm;

import java.security.PrivateKey;
import java.util.Date;

import org.joda.time.DateTime;

import com.platform.auth.common.util.jwt.IJWTInfo;
import com.platform.auth.common.util.jwt.JWTHelper;
import com.platform.auth.model.model.response.Token;
import com.platform.system.common.util.RsaKeyHelper;


/**
 * token工具类基类
 * @version: 1.0
 */
public abstract class BaseTokenUtil {

    /**
     * 获取失效时间
     * @return
     */
    protected abstract int getExpireTime();

    /**
     * 获取加密私钥字节码
     * @param keyConfig
     * @return
     */
    protected abstract byte[] getPrivateKeyByte();

    /**
     * 获取加密公钥字节码
     * @param keyConfig
     * @return
     */
    protected abstract byte[] getPublicKeyByte();
    

    /**
     * 密钥加密token
     *
     * @param jwtInfo token信息
     * @param priKeyPath 密钥文件路径
     * @param expire 过期时间
     * @return
     * @throws Exception
     */
    protected Token generateToken(IJWTInfo jwtInfo, String priKeyPath, int expire) throws Exception{
        PrivateKey key = RsaKeyHelper.getPrivateKey(priKeyPath);
        Date expireDate = DateTime.now().plusSeconds(expire).toDate();
        String accessToken = JWTHelper.generateJwtBuilder(jwtInfo, expireDate).signWith(SignatureAlgorithm.RS256, key).compact();
        return new Token(accessToken, expireDate);
    }

    /**
     * 密钥加密token
     *
     * @param jwtInfo token信息
     * @param priKey 密钥
     * @param expire 过期时间
     * @return
     * @throws Exception
     */
    protected Token generateToken(IJWTInfo jwtInfo, byte priKey[], int expire) throws Exception{
        PrivateKey key = RsaKeyHelper.getPrivateKey(priKey);
        Date expireDate = DateTime.now().plusSeconds(expire).toDate();
        String accessToken = JWTHelper.generateJwtBuilder(jwtInfo, expireDate).signWith(SignatureAlgorithm.RS256, key).compact();
        return new Token(accessToken, expireDate);
    }

    /**
     * 生成token
     * @param jwtInfo
     * @return
     * @throws Exception
     */
    public Token generateToken(IJWTInfo jwtInfo) throws Exception{
        Token token = generateToken(jwtInfo, getPrivateKeyByte(), getExpireTime());
        return token;
    }

    /**
     * 从token获取信息
     * @param token
     * @return
     * @throws Exception
     */
    public IJWTInfo getInfoFromToken(String token) throws Exception{
        return JWTHelper.parseToken(token, getPublicKeyByte());
    }

    /**
     * 刷新token
     * 刷新后,旧token会失效
     * @param oldToken 旧token
     * @return 新token
     */
    public Token refresh(String oldToken) throws Exception{
        IJWTInfo ijwtInfo = getInfoFromToken(oldToken);
        return generateToken(ijwtInfo);
    }
    
    /**
     * 注销token
     * @param token
     */
    public void invalid(String token){
        // 默认的jwt方式不需要做任何处理
    }
}
