package com.platform.auth.common.util.jwt;

import java.io.Serializable;

/**
 * 登陆请求VO
 * @version: 1.0
 */
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;


    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
