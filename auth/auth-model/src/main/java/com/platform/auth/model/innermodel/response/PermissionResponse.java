package com.platform.auth.model.innermodel.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @version: 1.0
 */
@Getter
@Setter
public class PermissionResponse implements Serializable {

	private static final long serialVersionUID = 3259610594594444532L;
	
	/**
	 * 所有请求方法
	 */
	public static final String ALL_METHOD = "all";

	/**
	 * 资源ID
	 */
	private String idRes;

	/**
	 * 资源名称
	 */
	private String resName;

	/**
	 * 资源类型（0：菜单; 1: 按钮; 2: 接口; 3:按钮页面）
	 */
	private String type;

	/**
	 * 资源编码
	 */
	private String code;

	/**
	 * 资源URL
	 */
	private String url;

	/**
	 * 请求方法(GET\POST\PUT\DELETE\等)
	 */
	private String method;

	/**
	 * 是否权限控制(0: 不控制, 1: 控制)
	 */
	private boolean isPower;

	/**
	 * 是否记录(0: 不记录，1: 记录)
	 */
	private boolean isRecord;
}
