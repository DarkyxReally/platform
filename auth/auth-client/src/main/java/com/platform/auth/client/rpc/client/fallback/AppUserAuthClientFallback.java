package com.platform.auth.client.rpc.client.fallback;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.platform.auth.client.rpc.client.AppUserAuthClient;
import com.platform.auth.client.rpc.client.AuthServiceId;
import com.platform.auth.model.model.TokenUserInfo;
import com.platform.auth.model.model.provider.response.TokenInfoResponse;
import com.platform.auth.model.model.response.Token;
import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.util.Shit;
import com.platform.system.common.web.response.entity.BaseResponse;


@Slf4j
@Component
public class AppUserAuthClientFallback implements AppUserAuthClient {

    @Value(AuthServiceId.SERVICE_ID)
    private String serviceId;

    /**
     * 请求没有响应
     */
    private void didNotGetResponse(String method){
        log.error("请求服务端:{},接口:{}失败，执行fallback方法", serviceId, method);
    }

    @Override
    public BaseResponse<Token> createTokenByIdUser(String idUser){
        didNotGetResponse("BaseResponse<Token> createTokenByIdUser(String idUser)");
        log.error("根据用户id获取APP用户token失败.");
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }
//
//    @Override
//    public BaseResponse<Boolean> invalid(String token){
//        didNotGetResponse("BaseResponse<Token> invalid(String token)");
//        log.error("APP用户注销token失败.");
//        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
//        return null;
//    }

    @Override
    public BaseResponse<TokenUserInfo> verify(String token){
        didNotGetResponse("BaseResponse<Boolean> verify(String token)");
        log.error("验证token失败.");
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }

    @Override
    public BaseResponse<TokenInfoResponse> info(String token){
        didNotGetResponse("BaseResponse<TokenInfoResponse> info(String token)");
        log.error("查询token信息失败.");
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
    }
}
