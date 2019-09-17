package com.platform.auth.model.enums.login;

/**
 * 登陆场景<br>
 * -1: 正常退出<br>
 * 
 * @version: 1.0
 */
public interface ILogoutSence {

    /**
     * 场景code
     * @return
     */
    int code();

    /**
     * 场景枚举名
     */
    String name();

    /**
     * 场景描述信息
     */
    String message();
}
