package com.platform.user.innermodel.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: 李文斌
 * @since: 2019年8月23日下午5:13:28
 * @version: 1.0
 */
@Getter
@Setter
public class UserRegisterResponse implements Serializable {

    private static final long serialVersionUID = -1005531887930733815L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 账号
     */
    private String account;
    
    /**
     * 类型
     */
    private String type;

    /**
     * 名称
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    
}
