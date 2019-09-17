package com.platform.auth.service.impl.util.client;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.platform.auth.common.config.AuthClientConfig;
import com.platform.auth.common.util.jwt.IJWTInfo;
import com.platform.auth.model.model.response.Token;
import com.platform.auth.model.model.response.constant.RedisAuthKeyConstants;
import com.platform.auth.service.impl.util.CacheableTokenUtil;
import com.platform.system.common.json.JsonUtil;

/**
 * 客户端Token工具类
 * @version: 1.0
 */
@Configuration
public class ClientTokenUtil extends CacheableTokenUtil{

    /**
     * 加密信息配置
     */
    @Autowired
    private AuthClientConfig clientConfig;

    @Autowired
    private StringRedisTemplate redisTemplate;
    
    /**
     * token缓存key
     * @param token
     * @return
     */
    private String getCacheKey(String token){
        return RedisAuthKeyConstants.TOKEN_CLIENTID_KEY_ + token;
    }

    @Override
    protected int getExpireTime(){
        return clientConfig.getExpire();
    }

    @Override
    protected byte[] getPrivateKeyByte(){
        return clientConfig.getPrivateKey();
    }

    @Override
    protected byte[] getPublicKeyByte(){
        return clientConfig.getPublicKey();
    }

    @Override
    protected void cacheToken(Token token, IJWTInfo jwtInfo){
        String cacheKey = getCacheKey(token.getAccessToken());
        long expired = (token.getExpire().getTime() - System.currentTimeMillis()) / 1000;
        BoundValueOperations<String,String> valueOps = redisTemplate.boundValueOps(cacheKey);
        valueOps.set(JsonUtil.toJSONString(jwtInfo), expired, TimeUnit.SECONDS);
    }

    @Override
    protected boolean isTokenExists(String token){
        String tokenUserIdKey = getCacheKey(token);
        return Boolean.TRUE.equals(redisTemplate.hasKey(tokenUserIdKey));
    }
    
    @Override
    public void invalid(String token){
        redisTemplate.delete(getCacheKey(token));
    }

    /**
     * 刷新token
     * 刷新后,旧token会失效
     * @param oldToken 旧token
     * @return 新token
     */
    @Override
    public Token refresh(String oldToken) throws Exception{
        // 若缓存中不存在,说明旧token以过期, 不能刷新
        if (!isTokenExists(oldToken)){
            return null;
        }
        // 解析token
        IJWTInfo ijwtInfo = getInfoFromToken(oldToken);
        // 旧token失效
        invalid(oldToken);
        // 根据用户信息重新生成token
        return generateToken(ijwtInfo);
    }
}
