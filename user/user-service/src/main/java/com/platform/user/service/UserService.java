package com.platform.user.service;

import com.platform.user.innermodel.request.UserRegisterRequest;
import com.platform.user.innermodel.response.UserInfoResponse;

/**
 * @author: 李文斌
 * @since: 2019年8月26日上午9:15:26
 * @version: 1.0
 */
public interface UserService {
	/**
	 * @description：新增注册用户
	 * @author：李文斌
	 * @since： 2019年8月26日上午9:18:36
	 */
	String addUser(UserRegisterRequest registerReq);
	
	UserInfoResponse selectByUnionId(String unionId);
	
	UserInfoResponse selectByUserId(String userId);
}
