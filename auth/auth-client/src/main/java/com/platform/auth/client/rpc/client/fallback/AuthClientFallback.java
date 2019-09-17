package com.platform.auth.client.rpc.client.fallback;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.platform.auth.client.rpc.client.AuthClient;
import com.platform.auth.client.rpc.client.AuthServiceId;
import com.platform.auth.common.util.jwt.LoginDTO;
import com.platform.auth.model.model.response.Token;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.util.Shit;
import com.platform.system.common.web.response.entity.CollectionResponse;
import com.platform.system.common.web.response.entity.ObjectResponse;


@Component
@Slf4j
public class AuthClientFallback implements AuthClient {

    @Value(AuthServiceId.SERVICE_ID)
    private String serviceId;

    /**
     * 请求没有响应
     */
    private void didNotGetResponse(String method){
        log.error("请求服务端:{},接口:{}失败，执行fallback方法", serviceId, method);
    }

    @Override
    public CollectionResponse<String> getAllowedClient(String serviceId, String secret){
        didNotGetResponse("getAllowedClient(String serviceId, String secret)");
        log.error("获取允许访问的服务列表失败.");
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public ObjectResponse<Token> token(LoginDTO loginDTO){
        didNotGetResponse("token(LoginDTO loginDTO)");
        log.error("获取客户端:{}验证token失败.", loginDTO.getUsername());
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public ObjectResponse<byte[]> getServicePublicKey(String clientId, String secret){
        didNotGetResponse("getServicePublicKey(String clientId, String secret)");
        log.error("获取服务:{} 服务公钥失败.", clientId);
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public ObjectResponse<byte[]> getUserPublicKey(String clientId, String secret){
        didNotGetResponse("getUserPublicKey(String clientId, String secret)");
        log.error("获取服务:{} 用户公钥失败.", clientId);
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }


}
