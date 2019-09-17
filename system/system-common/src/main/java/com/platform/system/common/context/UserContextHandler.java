package com.platform.system.common.context;

import com.platform.system.common.context.user.IUserDetail;

/**
 * 用户信息上下文处理器
 * @version: 1.0
 */
public class UserContextHandler {

    /**
     * 当前线程上下文线程变量,子线程不能获取该线程变量数据
     */
    private static ThreadLocal<IUserDetail> threadLocal = new ThreadLocal<IUserDetail>();
    
    /**
     * 设置上下文数据
     * @param key
     * @param value
     */
    public static void set(IUserDetail userInfo){
        threadLocal.set(userInfo);
    }

    /**
     * 获取上下文数据
     * @param key
     */
    public static IUserDetail get(){
        return threadLocal.get();
    }

    /**
     * 移除线程变量
     */
    public static void remove(){
        threadLocal.remove();
    }
}
