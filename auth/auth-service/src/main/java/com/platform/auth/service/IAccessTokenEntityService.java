package com.platform.auth.service;

import java.util.Date;
import java.util.List;

import com.platform.auth.entity.AccessTokenEntity;
import com.platform.auth.model.innermodel.TerminalChannelConstant.TerminalChannel;
import com.platform.auth.model.model.constant.UserTypeConstant.UserType;


/**
 * AccessToken实体接口
 * @version: 1.0
 */
public interface IAccessTokenEntityService extends ICrudService<AccessTokenEntity> {
	
	/**
     * 查询用户的有效accessToken
     * @param idUser
     * @param channel 终端渠道
     * @return
     */
    List<AccessTokenEntity> queryUserValidTokens(String idUser, TerminalChannel channel);

    /**
     * 新增token
     * @param idUser
     * @param expire
     * @param platform
     * @param channel 终端渠道
     * @param userType 用户类型
     * @return tokenId
     */
    String insertToken(String idUser, Date expire, String platform, TerminalChannel channel, UserType userType);

    /**
     * 判断token是否有效
     * @param idToken
     * @return
     */
    boolean isValid(String idToken);

    /**
     * 获取token实体
     * @param idToken tokenid
     * @param idUser 用户id
     * @return
     */
    AccessTokenEntity getTokenEntity(String idToken);

    /**
     * 将token修改为失效
     * @param idToken 
     * @return
     */
    int updateToInvalid(String idToken);

    /**
     * 将token修改为失效
     * @param idToken
     * @param idUser 用户id
     * @param tokenType
     * @return
     */
    int updateToInvalid(String idToken, String idUser);

    /**
     * 将token修改为失效
     * @param idTokens
     * @return
     */
    int updateToInvalid(List<String> idTokens);
    
    /**
     * 将用户的token修改为失效
     * @param idUser
     * @return
     */
    int updateTokenToInvalidByIdUser(String idUser);
    
    /**
     * 将用户的token修改为失效
     * @param idUser
     * @param channel 终端渠道
     * @return
     */
    int updateTokenToInvalidByIdUser(String idUser, TerminalChannel channel);

    /**
     * 将token修改为失效
     * @param idTokens
     * @param idUser 用户id
     * @return
     */
    int updateToInvalid(List<String> idTokens, String idUser);
}
