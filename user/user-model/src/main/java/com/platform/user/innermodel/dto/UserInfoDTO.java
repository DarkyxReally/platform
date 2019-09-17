package com.platform.user.innermodel.dto;

import com.platform.user.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO extends UserEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6711530796544211317L;
	/**
	 * 第三方头像
	 */
	private String url;
}
