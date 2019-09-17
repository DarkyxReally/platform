package com.platform.auth.model.enums.login;

/**
 * 登陆场景<br>
 * -1: 退出<br>
 * 1-20: 内部登陆<br>
 * 20-100: 第三方登陆<br>
 * 20-30: 腾讯<br>
 * 30-40: xxx<br>
 * 
 * @version: 1.0
 */
public interface ILoginSence {

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
