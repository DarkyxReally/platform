package com.platform.user.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.platform.system.common.web.response.entity.BaseResponse;
import com.platform.user.client.fallback.WechatUserClientFallback;
import com.platform.user.innermodel.request.UserRegisterRequest;
import com.platform.user.innermodel.response.UserInfoResponse;

/**
 * @author: 微信小程序用户处理
 * @since: 2019年8月30日上午11:57:01
 * @version: 1.0
 */
@FeignClient(name = "${platform.service-id.user}", fallback = WechatUserClientFallback.class)
public interface WechatUserClient {
	/**
     * API前缀
     */
    String API_PATH = "/wechat/user";
    
    @RequestMapping(value = {API_PATH+"/getUserByUnionId"})
    BaseResponse<UserInfoResponse> getUserByUnionId(@RequestParam("unionId") String unionId);
    
    @RequestMapping(value = {API_PATH+"/registerUser"}, method = RequestMethod.POST)
    BaseResponse<String> registerUser(@RequestBody UserRegisterRequest registerReq);
    
    @RequestMapping(value = {API_PATH+"/getUserByUserId"})
    BaseResponse<UserInfoResponse> getUserByUserId(@RequestParam("userId") String userId);
}
