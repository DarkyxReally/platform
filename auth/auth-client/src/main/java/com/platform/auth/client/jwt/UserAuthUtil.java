package com.platform.auth.client.jwt;

import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;

import com.platform.auth.common.config.AuthUserConfig;
import com.platform.auth.common.exception.JwtIllegalArgumentException;
import com.platform.auth.common.exception.JwtSignatureException;
import com.platform.auth.common.exception.JwtTokenExpiredException;
import com.platform.auth.common.util.jwt.IJWTInfo;
import com.platform.auth.common.util.jwt.JWTHelper;
import com.platform.system.common.util.RsaKeyHelper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;


/**
 * 用户认证工具类
 * @version: 1.0
 */
public class UserAuthUtil {

    @Autowired
    private AuthUserConfig userConfig;

    /**
     * 解析获取token信息
     * @param token
     * @return
     * @throws Exception
     */
    public IJWTInfo parseToken(String token) throws Exception{
        try{
            return JWTHelper.parseToken(token, userConfig.getPublicKey());
        }catch(ExpiredJwtException ex){
            throw new JwtTokenExpiredException("User token expired!");
        }catch(SignatureException ex){
            throw new JwtSignatureException("User token signature error!");
        }catch(IllegalArgumentException ex){
            throw new JwtIllegalArgumentException("User token is null or empty!");
        }
    }
    
    /**
     * 解析获取token信息
     * @param token
     * @return
     * @throws Exception
     */
    public Claims parseTokenInfo(String token) throws Exception{
        try{
            PublicKey key = RsaKeyHelper.getPublicKey(userConfig.getPublicKey());
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return claimsJws.getBody();
        }catch(ExpiredJwtException ex){
            throw new JwtTokenExpiredException("User token expired!");
        }catch(SignatureException ex){
            throw new JwtSignatureException("User token signature error!");
        }catch(IllegalArgumentException ex){
            throw new JwtIllegalArgumentException("User token is null or empty!");
        }
    }
}
