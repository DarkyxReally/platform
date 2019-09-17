package com.platform.auth.common.userdetail;

import javax.servlet.http.HttpServletRequest;

import com.platform.system.common.context.user.IUserDetail;


/**
 * 用户信息接口
 * @version: 1.0
 */
public interface IUserDetailService {

    /**
     * 获取用户
     * @version: 1.0
     * @param request
     * @return
     */
    IUserDetail getUser(HttpServletRequest request);
    
    /**
     * 获取用户信息
     * @version: 1.0
     * @param userInfo
     * @return
     */
    IUserDetail getUser(String userInfo);
    
    /**
     * 编码用户信息
     * @version: 1.0
     * @param user
     * @return
     */
    String encodeUser(IUserDetail user);
}
