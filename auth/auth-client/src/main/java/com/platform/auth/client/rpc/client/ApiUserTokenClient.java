package com.platform.auth.client.rpc.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.platform.auth.client.rpc.client.fallback.ApiUserTokenClientFallback;
import com.platform.auth.model.model.provider.response.TokenInfoResponse;
import com.platform.system.common.web.response.entity.BaseResponse;

/**
 * API用户token
 * 
 * @version: 1.0
 */
@FeignClient(name = AuthServiceId.SERVICE_ID, fallback = ApiUserTokenClientFallback.class)
public interface ApiUserTokenClient {

    String API_PATH = "/api/jwt/user";

    /**
     * 获取token信息
     * 
     * @version: 1.0
     * @return
     */
    @RequestMapping(value = API_PATH + "/info", method = RequestMethod.GET)
    BaseResponse<TokenInfoResponse> info(@RequestParam("token") String token);
}
