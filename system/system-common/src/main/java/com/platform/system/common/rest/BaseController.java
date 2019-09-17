package com.platform.system.common.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.platform.system.common.context.UserContextHandler;


/**
 * 基础Controller
 * @version: 1.0
 */
public abstract class BaseController extends BaseProvider{
    
    @Autowired
    protected HttpServletRequest request;

    /**
     * 获取当前用户昵称
     * @return
     */
    public String getCurrentUserNickName(){
        return UserContextHandler.get().nickName();
    }
    
    /**
     * 获取当前用户ID
     * @return
     */
    public String getCurrentUserId(){
        return UserContextHandler.get().userId();
    }
}
