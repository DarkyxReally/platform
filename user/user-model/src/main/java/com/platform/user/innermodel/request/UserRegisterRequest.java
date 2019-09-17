package com.platform.user.innermodel.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: 李文斌
 * @since: 2019年8月23日下午4:51:23
 * @version: 1.0
 */
@Getter
@Setter
public class UserRegisterRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2463384650517975335L;
	
    /**
     * 用户名称
     */
	/* @NotBlank(message = "用户名称不能为空") */
    private String name;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 第三方id标识
     */
    private String thirdUuid;
    
    /**
     * 第三方头像
     */
    private String thirdUrl;
}
