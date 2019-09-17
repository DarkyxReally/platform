package com.platform.user.bs;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.platform.system.common.enums.rest.StatusCode;
import com.platform.system.common.rest.BaseController;
import com.platform.system.common.web.response.entity.BaseResponse;
import com.platform.user.innermodel.request.UserRegisterRequest;
import com.platform.user.innermodel.response.UserInfoResponse;
import com.platform.user.innermodel.response.UserRegisterResponse;
import com.platform.user.service.UserService;

/**
 * 微信小程序用户请求处理
 * @author: 李文斌
 * @since: 2019年8月23日下午4:33:24
 * @version: 1.0
 */
@Slf4j
/* @AuthUserStatus(requireUser = false) */
@RestController
@RequestMapping("/wechat/user")
public class WechatUserController extends BaseController{
	@Autowired
    private UserService userService;
	/**
	 * @description：注册用户
	 * @author：李文斌
	 * @since： 2019年8月29日下午6:55:06
	 * @param：registerReq(用户名称,手机号,第三方id标识,第三方头像)
	 */
    @RequestMapping(value = {"/registerUser"}, method = RequestMethod.POST)
    public BaseResponse<String> registerUser(@RequestBody UserRegisterRequest registerReq){
        log.info("=======> 开始调用注册接口:{}",registerReq.getName());
        UserRegisterResponse userResponse = new UserRegisterResponse();
        String userId = "";
        try {
        	userId = userService.addUser(registerReq);
        	userResponse.setUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return fail(StatusCode.OPERATION_FAIL);
		}
        return success(userId);
    }
    /**
     * @description：根据unionId查询用户信息
     * @author：李文斌
     * @since： 2019年8月29日上午9:00:06
     * @param：unionId
     */
    @RequestMapping(value = {"/getUserByUnionId"})
    public BaseResponse<UserInfoResponse> getUserByUnionId(@RequestParam("unionId") String unionId){
        log.info("=======> 调用根据unionId查询用户信息接口参数:{}",unionId);
        if(StringUtils.isBlank(unionId)) {
			return fail(StatusCode.RET_PARAMERROR);
		}
        UserInfoResponse re = new UserInfoResponse();
        try {
        	re = userService.selectByUnionId(unionId);
		} catch (Exception e) {
			e.printStackTrace();
			return fail(StatusCode.OPERATION_FAIL);
		}
        return success(re);
    }
    
    /**
     * @description：根据用户id查询用户
     * @author：李文斌
     * @since： 2019年9月6日下午4:53:29
     * @param：用户id
     */
    @RequestMapping(value = {"/getUserByUserId"})
    public BaseResponse<UserInfoResponse> getUserByUserId(@RequestParam("userId") String userId){
        log.info("=======> 调用根据用户id查询用户信息接口参数:{}",userId);
        if(StringUtils.isBlank(userId)) {
			return fail(StatusCode.RET_PARAMERROR);
		}
        UserInfoResponse re = new UserInfoResponse();
        try {
        	re = userService.selectByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return fail(StatusCode.OPERATION_FAIL);
		}
        return success(re);
    }
}
