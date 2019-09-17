package com.platform.api.gate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.platform.auth.client.jwt.UserAuthUtil;
import com.platform.auth.client.rpc.client.ApiUserTokenClient;
import com.platform.auth.common.exception.NotSupportAuthException;
import com.platform.auth.model.model.provider.response.TokenInfoResponse;
import com.platform.auth.model.model.response.constant.UserTypeConstant.UserType;
import com.platform.system.common.constant.CommonConstants;
import com.platform.system.common.context.user.AppUserDetail;
import com.platform.system.common.context.user.AuthUserTypeConstant.AuthUserType;
import com.platform.system.common.context.user.UserDetail;
import com.platform.system.common.context.user.WechatminiUserDetail;
import com.platform.system.common.json.JsonUtil;
import com.platform.system.common.util.CommonUtil;
import com.platform.system.common.web.response.entity.BaseResponse;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

/**
 * token服务
 * 
 * @version: 1.0
 */
@Slf4j
@Component
public class TokenServiceImpl {

    /**
     * 用户token缓存
     */
    private static final String USER_TOKEN_CACHE_PREFIX = "AUTH:USER_TOKEN_NEW:";

    /**
     * 用户类型
     */
    private static final String USER_TYPE = "userType";
    /**
     * 用户信息
     */
    private static final String USER_INFO = "userInfo";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    private ApiUserTokenClient apiUserTokenClient;

    /**
     * 用户token缓存key
     * 
     * @param idToken
     * @param idUser
     * @return
     */
    private String getUserTokenKey(String idToken, String idUser){
        return USER_TOKEN_CACHE_PREFIX + idUser + CommonConstants.COLON + idToken;
    }

    /**
     * 获取缓存操作类
     * 
     * @version: 1.0
     * @param idToken
     * @param idUser
     * @return
     */
    private BoundValueOperations<String, String> getCacheOps(String idToken, String idUser){
        String cacheKey = this.getUserTokenKey(idToken, idUser);
        return stringRedisTemplate.boundValueOps(cacheKey);
    }

    /**
     * 从缓存中获取用户
     * 
     * @param idToken tokenId
     * @param idUser 用户id
     * @return
     */
    private UserDetail getUserDetailFromCache(String idToken, String idUser){
        BoundValueOperations<String, String> valueOps = getCacheOps(idToken, idUser);
        String string = valueOps.get();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(string)){
            JSONObject jsonObject = JSONObject.parseObject(string);
            try{
                UserType userType = UserType.valueOfCode(jsonObject.getString(USER_TYPE));
                return getUserDetail(userType, jsonObject.getString(USER_INFO));
            }catch(IllegalArgumentException e){
                log.error("不支持的token用户类型:{}", jsonObject);
            }
        }

        return null;
    }

    /**
     * 查询用户详情
     * @version: 1.0
     * @param token token字符串
     * @param idToken tokenId
     * @param idUser 用户id
     * @param expDate 过期时间
     * @return
     */
    private UserDetail queryUserDetail(String token, String idToken, String idUser, Date expDate){
        BaseResponse<TokenInfoResponse> verifyResp = apiUserTokenClient.info(token);
        if(verifyResp.isSuccessCode()){
            UserDetail userDetail = null;
            TokenInfoResponse tokenInfo = verifyResp.getData();
            if(null != tokenInfo){
                try{
                	//目前只针对小程序用户处理
                	userDetail = JSONObject.parseObject(tokenInfo.getUserJsonInfo(), WechatminiUserDetail.class);
                	cacheUserInfo(idToken, userDetail, tokenInfo.getExpireDate());
                }catch(IllegalArgumentException e){
                    log.error("不支持的token用户类型:{}", tokenInfo);
                    throw new NotSupportAuthException("暂不支持该用户类型");
                }
            }
            return userDetail;
        }else{
            log.info("验证token失败:{}", verifyResp);
            return null;
        }
    }

    /**
     * 获取用户类型
     * 
     * @version: 1.0
     * @param userType
     * @param userInfo
     * @return
     */
    private UserDetail getUserDetail(UserType userType, String userInfo){
        UserDetail userDetail = null;
        if(null != userInfo){
            switch(userType){
                case APP_USER:
                    userDetail = JSONObject.parseObject(userInfo, AppUserDetail.class);
                    break;
                default:
                    log.error("不支持该用户类型:{}", userType);
                    throw new NotSupportAuthException("暂不支持该用户类型");
            }
        }
        return userDetail;
    }

    /**
     * 销毁token
     * 
     * @param idUser 用户id
     */
    public void destroyToken(String idUser){
        destroyToken(idUser, null);
    }

    /**
     * 销毁token 当idTokens不为空时, 只有两个条件同时满足才能销毁
     * 
     * @param idUser 用户id
     * @param idTokens tokenId
     */
    public void destroyToken(String idUser, List<String> idTokens){
        if(StringUtils.isBlank(idUser)){
            // 参数不对
            return;
        }
        if(CollectionUtils.isEmpty(idTokens)){
            // 没有token, 删除该用户所有token
            String tokenKens = USER_TOKEN_CACHE_PREFIX + idUser + CommonConstants.COLON + "*";
            Set<String> keys = stringRedisTemplate.keys(tokenKens);
            if(CollectionUtils.isNotEmpty(keys)){
                for(String key : keys){
                    stringRedisTemplate.delete(key);
                }
            }
        }else{
            // 删除指定token
            List<String> tokenKeys = new ArrayList<String>();
            for(String idToken : idTokens){
                if(StringUtils.isBlank(idToken)){
                    continue;
                }
                tokenKeys.add(this.getUserTokenKey(idToken, idUser));
            }

            if(CollectionUtils.isNotEmpty(tokenKeys)){
                // 分批删除
                List<List<String>> splitList = CommonUtil.splitList(tokenKeys, 200);
                for(List<String> list : splitList){
                    stringRedisTemplate.delete(list);
                }
            }
        }
    }

    /**
     * 获取用户id(安全调用,不会抛异常)
     * 
     * @param token
     * @return
     */
    @Deprecated
    public String getUserIdSafety(String token){
        try{
            return getUserId(token);
        }catch(Exception e){
            return null;
        }
    }

    /**
     * 获取APP用户id
     * 
     * @param token
     * @return
     * @throws Exception
     */
    @Deprecated
    public String getUserId(String token) throws Exception{
        UserDetail userDetail = parseTokenUserInfo(token);
        if(null != userDetail){
            if (userDetail.userType() == AuthUserType.APP_USER){
                // 目前调用这个接口的都是APP用户
                return userDetail.getIdUser();
            }
        }
        return null;
    }

    /**
     * 解析token用户信息
     * 
     * @param token
     * @return
     * @throws Exception
     */
    public UserDetail parseTokenUserInfo(String token) throws Exception{
        UserDetail userInfo = null;
        Claims claims = userAuthUtil.parseTokenInfo(token);
        // tokenId
        String idToken = claims.getId();
        // 用户id
        String idUser = claims.getSubject();
        if(StringUtils.isNotBlank(idToken) && StringUtils.isNotBlank(idUser)){
            // 验证在缓存中是否有效
            userInfo = this.getUserDetailFromCache(idToken, idUser);
            if(null != userInfo){
                // 缓存中仍有效
                return userInfo;
            }
            // 缓存中没有信息, 实时查询接口
            if(null != claims.getExpiration()){
                userInfo = this.queryUserDetail(token, idToken, idUser, claims.getExpiration());
            }else{
                // 没有设置有效期
                if(log.isDebugEnabled()){
                    log.debug("token已失效");
                }
            }
        }
        return userInfo;
    }

    /**
     * 缓存用户信息
     * 
     * @version: 1.0
     * @param idToken
     * @param user
     */
    private void cacheUserInfo(String idToken, UserDetail user, Date expireDate){
        Map<String, String> params = new HashMap<String, String>();
        params.put(USER_TYPE, user.userType().strCode());
        params.put(USER_INFO, JsonUtil.toJSONString(user));
        String jsonString = JsonUtil.toJSONString(params);
        String idUser = user.getIdUser();
        // 距离有效期还有多少分钟
        long minutes = (expireDate.getTime() - System.currentTimeMillis()) / (1000 * 60);
        // 有效期至少需要1分钟以上
        if(minutes > 10){
            // 有效期还有10分钟以上的缓存10分钟即可
            getCacheOps(idToken, idUser).set(jsonString, 10, TimeUnit.MINUTES);
        }else{
            // 缓存实际的分钟数
            getCacheOps(idToken, idUser).set(jsonString, minutes, TimeUnit.MINUTES);
        }
    }

}
