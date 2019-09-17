package com.platform.auth.common.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.platform.system.common.util.RsaKeyHelper;


/**
 * JWT 工具类
 * @version: 1.0
 */
public class JWTHelper {

    /**
     * 根据信息生成jwt构造器
     * @param jwtInfo
     * @param expire 失效时间
     * @return
     */
    public static JwtBuilder generateJwtBuilder(IJWTInfo jwtInfo, Date expire){
        JwtBuilder jwtBuilder = Jwts.builder().setSubject(jwtInfo.getId());
        Map<String, String> extendParam = jwtInfo.infos();
        if(null != extendParam){
            for(Entry<String, String> entry : extendParam.entrySet()){
                if (null != entry){
                    jwtBuilder.claim(entry.getKey(), entry.getValue());
                }
            }
        }

        jwtBuilder.setExpiration(expire);
        return jwtBuilder;
    }

    /**
     * 解析Jws信息
     * @param claimsJws
     * @return
     */
    private static IJWTInfo parseJWTInfo(Jws<Claims> claimsJws){
        Claims body = claimsJws.getBody();
        return new JWTInfo(body.getSubject(), body);
    }

    /**
     * 公钥解析token
     * @param token token
     * @param pubKeyPath 公钥文件路径
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, String pubKeyPath) throws Exception{
        PublicKey key = RsaKeyHelper.getPublicKey(pubKeyPath);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, byte[] pubKey) throws Exception{
        PublicKey key = RsaKeyHelper.getPublicKey(pubKey);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param pubKeyPath
     * @return
     * @throws Exception
     */
    public static IJWTInfo getInfoFromToken(String token, String pubKeyPath) throws Exception{
        Jws<Claims> claimsJws = parserToken(token, pubKeyPath);
        return parseJWTInfo(claimsJws);
    }

    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param pubKey
     * @return
     * @throws Exception
     */
    public static IJWTInfo parseToken(String token, byte[] pubKey) throws Exception{
        Jws<Claims> claimsJws = parserToken(token, pubKey);
        return parseJWTInfo(claimsJws);
    }
}
