package com.platform.auth.client.rpc.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.platform.auth.client.rpc.client.fallback.AppUserAuthClientFallback;
import com.platform.auth.model.model.TokenUserInfo;
import com.platform.auth.model.model.provider.response.TokenInfoResponse;
import com.platform.auth.model.model.response.Token;
import com.platform.system.common.web.response.entity.BaseResponse;


/**
 * 认证服务客户端
 * @version: 1.0
 */
@FeignClient(name = AuthServiceId.SERVICE_ID, configuration = {}, fallback = AppUserAuthClientFallback.class)
public interface AppUserAuthClient {

    String API_PATH = "/jwt/user";

    /**
     * 根据用户id生成token
     * @param idUser
     * @return
     */
    @RequestMapping(value = API_PATH + "/tokenByUserId", method = RequestMethod.POST)
    BaseResponse<Token> createTokenByIdUser(@RequestParam("userId") String idUser);
    
    /**
     * 验证token合法性
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = API_PATH + "/verify", method = RequestMethod.POST)
    BaseResponse<TokenUserInfo> verify(@RequestParam("token") String token);
    
    /**
     * 获取token信息
     * @version: 1.0
     * @param token
     * @return
     */
    @RequestMapping(value = API_PATH + "/info", method = RequestMethod.GET)
    BaseResponse<TokenInfoResponse> info(@RequestParam("token")String token);
}
