package com.platform.auth.client.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.platform.auth.client.rpc.client.AuthClient;
import com.platform.auth.common.config.AuthClientConfig;
import com.platform.auth.common.event.AuthRemoteEvent;
import com.platform.auth.common.exception.JwtIllegalArgumentException;
import com.platform.auth.common.exception.JwtSignatureException;
import com.platform.auth.common.exception.JwtTokenExpiredException;
import com.platform.auth.common.util.jwt.IJWTInfo;
import com.platform.auth.common.util.jwt.JWTHelper;
import com.platform.auth.common.util.jwt.LoginDTO;
import com.platform.auth.model.model.response.Token;
import com.platform.system.common.json.JsonUtil;
import com.platform.system.common.web.response.entity.CollectionResponse;
import com.platform.system.common.web.response.entity.ObjectResponse;


/**
 * 服务认证工具类
 * @version: 1.0
 */
@Slf4j
@EnableScheduling
public class ServiceAuthUtil implements ApplicationListener<AuthRemoteEvent> {

    @Autowired
    private AuthClientConfig clientConfig;
    @Autowired
    private AuthClient authClient;

    private List<String> allowedClient;

    private String clientToken;

    public IJWTInfo parseToken(String token) throws Exception{
        try{
            return JWTHelper.parseToken(token, clientConfig.getPublicKey());
        }catch(ExpiredJwtException ex){
            throw new JwtTokenExpiredException("Client token expired!");
        }catch(SignatureException ex){
            throw new JwtSignatureException("Client token signature error!");
        }catch(IllegalArgumentException ex){
            throw new JwtIllegalArgumentException("Client token is null or empty!");
        }
    }

    /**
     * 刷新当前服务允许访问的服务列表
     */
    public void refreshAllowedClient(){
        log.info("refresh allowedClient.....");
        CollectionResponse<String> resp = authClient.getAllowedClient(clientConfig.getClientId(),
                clientConfig.getSecret());
        if(resp.isSuccessCode()){
            this.allowedClient = resp.getData();
        }
    }

    /**
     * 定时刷新客户端token
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void refreshClientToken(){
        log.info("refresh client token.....");
        ObjectResponse<Token> resp = authClient
                .token(new LoginDTO(clientConfig.getClientId(), clientConfig.getSecret()));
        if(resp.isSuccessCode()){
            this.clientToken = resp.getData().getAccessToken();
        }else{
            log.warn("获取认证客户端token失败,{}", JsonUtil.toJSONString(resp));
        }
    }

    public String getClientToken(){
        return clientToken;
    }

    public void setClientToken(String clientToken){
        this.clientToken = clientToken;
    }

    public List<String> getAllowedClient(){
        if(this.allowedClient == null){
            this.refreshAllowedClient();
        }
        return allowedClient;
    }

    @Override
    public void onApplicationEvent(AuthRemoteEvent authRemoteEvent){
        this.allowedClient = authRemoteEvent.getAllowedClient();
    }
}