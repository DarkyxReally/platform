package com.platform.auth.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.platform.auth.entity.AccessTokenEntity;
import com.platform.auth.model.innermodel.TerminalChannelConstant.TerminalChannel;
import com.platform.auth.model.innermodel.token.AccessToken;
import com.platform.auth.model.model.TokenUserInfo;
import com.platform.auth.model.model.constant.UserTypeConstant.UserType;
import com.platform.auth.service.IAccessTokenEntityService;
import com.platform.auth.service.IAccessTokenService;
import com.platform.system.common.util.RsaKeyHelper;


/**
 * token实现基类
 * @version: 1.0
 */
public abstract class BaseTokenServiceImpl implements IAccessTokenService {

    @Autowired
    protected IAccessTokenEntityService accessTokenEntityService;

    /**
     * 失效时间(单位秒)
     * @return
     */
    protected abstract int ttl();

    /**
     * 解析accessToken时是否进行校验
     * @return
     */
    protected boolean checkOnParseAccessToken(){
        return true;
    }

    /**
     * 获取失效时间
     * @return
     */
    private Date getExpireDateTime(){
        return DateTime.now().plusSeconds(ttl()).toDate();
    }

    /**
     * 获取加密私钥字节码
     * @return
     */
    protected abstract byte[] getPrivateKeyByte();

    /**
     * 获取加密公钥字节码
     * @return
     */
    protected abstract byte[] getPublicKeyByte();

    /**
     * 根据用户信息生成accessToken
     * @param idToken tokenId
     * @param userInfo 用户信息
     * @param expireDate 失效时间
     * @return
     * @throws Exception
     */
    private String generateAccessToken(String idToken, TokenUserInfo userInfo, Date expireDate) throws Exception{
        PrivateKey priKey = RsaKeyHelper.getPrivateKey(getPrivateKeyByte());
        String idUser = userInfo.getIdUser();
        JwtBuilder jwtBuilder = Jwts.builder().setSubject(idUser);
        // id为tokenId
        jwtBuilder.setId(idToken);
        // subject为用户id
        jwtBuilder.setSubject(idUser);
        // 有效期
        jwtBuilder.setExpiration(expireDate);
        return jwtBuilder.signWith(SignatureAlgorithm.RS256, priKey).compact();
    }

    @Override
    @Transactional
    public AccessToken createAccessToken(TokenUserInfo userInfo) throws Exception{
    	// 查询用户有效的token信息
        List<String> tokens = this.selectUserValidAccessTokens(userInfo.getIdUser(), userInfo.getTerminalChannel());
        if(CollectionUtils.isNotEmpty(tokens)){
            // 将token修改为失效
            accessTokenEntityService.updateToInvalid(tokens, userInfo.getIdUser());
        }
        Date expireDate = getExpireDateTime();
        // 新增token
        String tokenId = accessTokenEntityService.insertToken(userInfo.getIdUser(), expireDate, userInfo.getPlatform(), userInfo.getTerminalChannel(), userInfo.getUserType());
        // token加密
        String token = generateAccessToken(tokenId, userInfo, expireDate);
        AccessToken accessToken = new AccessToken();
        accessToken.setIdToken(tokenId);
        accessToken.setToken(token);
        accessToken.setExpire(expireDate);
        return accessToken;
    }
    
    /**
     * 查询用户有效的access token
     * @param idUser
     * @param channel 特定的终端渠道
     * @return
     */
    private List<String> selectUserValidAccessTokens(String idUser, TerminalChannel channel){
        List<String> tokens = new ArrayList<String>();
        List<AccessTokenEntity> entitys = accessTokenEntityService.queryUserValidTokens(idUser, channel);
        if(CollectionUtils.isNotEmpty(entitys)){
            for(AccessTokenEntity accessTokenEntity : entitys){
                tokens.add(accessTokenEntity.getTokenId());
            }
        }
        return tokens;
    }
    

    /**
     * 获取 access token 实体
     * @param idToken tokenId
     * @param idUser 用户id
     * @return
     */
    private AccessTokenEntity getTokenEntity(String idToken){
        return accessTokenEntityService.getTokenEntity(idToken);
    }

    /**
     * 解析token
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public TokenUserInfo getAccessTokenInfo(String token) throws Exception{
        PublicKey key = RsaKeyHelper.getPublicKey(getPublicKeyByte());
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        // tokenId
        String idToken = claims.getId();
        // 用户id
        String idUser = claims.getSubject();
        Date expiration = claims.getExpiration();
        AccessTokenEntity tokenEntity = this.getTokenEntity(idToken);
        // 开启校验合法性
        TokenUserInfo userInfo = null;
        if(tokenEntity != null){//id为idToken的token信息不存在
        	userInfo = new TokenUserInfo();
        	userInfo.setIdUser(idUser);
            userInfo.setIdToken(idToken);
            TerminalChannel t = TerminalChannel.WECHAT_MINI;
            userInfo.setTerminalChannel(TerminalChannel.valueOfCode(t.strCode()));
            userInfo.setExpired(expiration);
            userInfo.setUserType(UserType.WECHATMINI_USER);
        }
        return userInfo;
    }

    /**
     * 修改为失效
     * @param idToken
     * @param idUser
     */
    private void invalidAccessToken(String idToken, String idUser){
        accessTokenEntityService.updateToInvalid(idToken, idUser);
    }

    @Override
    public void invalidAccessToken(String token) throws Exception{
        // 解析token
        PublicKey key = RsaKeyHelper.getPublicKey(getPublicKeyByte());
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        // tokenId
        String idToken = claims.getId();
        // 用户id
        String idUser = claims.getSubject();
        // 使token失效
        this.invalidAccessToken(idToken, idUser);
    }
}
