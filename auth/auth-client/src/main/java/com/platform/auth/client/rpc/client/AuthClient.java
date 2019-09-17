package com.platform.auth.client.rpc.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.platform.auth.client.rpc.client.fallback.AuthClientFallback;
import com.platform.auth.common.util.jwt.LoginDTO;
import com.platform.auth.model.model.response.Token;
import com.platform.system.common.web.response.entity.CollectionResponse;
import com.platform.system.common.web.response.entity.ObjectResponse;

/**
 * 认证服务客户端
 * @version: 1.0
 */
@FeignClient(name = AuthServiceId.SERVICE_ID, configuration = {}, fallback = AuthClientFallback.class)
public interface AuthClient {
    
    String API_PATH = "/client";

    /**
     * 获取服务允许访问的服务列表
     * @param serviceId
     * @param secret
     * @return
     */
    @RequestMapping(value = API_PATH + "/myClient", method = RequestMethod.GET)
    CollectionResponse<String> getAllowedClient(@RequestParam("serviceId") String serviceId,
            @RequestParam("secret") String secret);

    /**
     * 获取请求服务所需的token
     * @param loginDTO
     * @param 
     * @return
     */
    @RequestMapping(value = API_PATH + "/token", method = RequestMethod.POST)
    ObjectResponse<Token> token(@RequestBody LoginDTO loginDTO);

    /**
     * 获取服务公钥
     * @param clientId
     * @param secret
     * @return
     */
    @RequestMapping(value = API_PATH + "/servicePubKey", method = RequestMethod.GET)
    ObjectResponse<byte[]> getServicePublicKey(@RequestParam("clientId") String clientId,
            @RequestParam("secret") String secret);

    /**
     * 获取用户公钥
     * @param clientId
     * @param secret
     * @return
     */
    @RequestMapping(value = API_PATH + "/userPubKey", method = RequestMethod.GET)
    ObjectResponse<byte[]> getUserPublicKey(@RequestParam("clientId") String clientId,
            @RequestParam("secret") String secret);
}
