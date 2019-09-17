package com.platform.user.client.fallback;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.util.Shit;
import com.platform.system.common.web.response.entity.BaseResponse;
import com.platform.user.client.WechatUserClient;
import com.platform.user.innermodel.request.UserRegisterRequest;
import com.platform.user.innermodel.response.UserInfoResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WechatUserClientFallback implements WechatUserClient{
	
	@Value("${platform.service-id.user}")
    private String serviceId;
	
	/**
     * 请求没有响应
     */
    private void didNotGetResponse(String method){
        log.error("请求服务端:{},接口:{}失败，执行fallback方法", serviceId, method);
    }

	@Override
	public BaseResponse<UserInfoResponse> getUserByUnionId(String unionId) {
		didNotGetResponse("getUserByUnionId(String unionId)");
        log.error("查询用户失败:{}", unionId);
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
        return null;
	}

	@Override
	public BaseResponse<String> registerUser(UserRegisterRequest registerReq) {
		didNotGetResponse("registerUser(UserRegisterRequest registerReq)");
        log.error("注册用户失败:{}", registerReq);
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
		return null;
	}

	@Override
	public BaseResponse<UserInfoResponse> getUserByUserId(String userId) {
		didNotGetResponse("getUserByUserId(String userId)");
        log.error("根据用户id查询用户失败:{}", userId);
        Shit.fatal(StatusCode.SERVER_IS_BUSY_NOW);
		return null;
	}
    
    
}
