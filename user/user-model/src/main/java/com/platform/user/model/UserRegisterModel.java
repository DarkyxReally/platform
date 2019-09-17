package com.platform.user.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * @author: 李文斌
 * @since: 2019年8月23日下午5:20:54
 * @version: 1.0
 */
@Getter
@Setter
public class UserRegisterModel implements Serializable {

    private static final long serialVersionUID = -6392090624815902501L;

    /**
     * 用户类型
     */
    private String type;

    /**
     * 名称
     */
    private String name;
    
    /**
     * 密码
     */
    private String password;


}
