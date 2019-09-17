package com.platform.auth.model.model.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WechatRegisterRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3673705650593242932L;
	
	/** 微信昵称  */
	private String nickname;
	
	/** 微信头像url */
	private String imgUrl;
	
	/** 微信授权参数  */
	private String jsCode;
	
	/** 加密数据 */
	private String encryptedData;
	
	/** 加密数据  */
	private String iv;
	
	/** 微信绑定手机号  */
	private String phone;
}
