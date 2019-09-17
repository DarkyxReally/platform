package com.platform.system.common.json;

import java.io.Serializable;

/**
 * 响应结果对象
 * 
 */
public class Result implements Serializable {
	private static final long serialVersionUID = 6288374846131788743L;

	/**
	 * 信息
	 */
	private String tip;

	/**
	 * 状态码
	 */
	private int status;

	/**
	 * 错误码
	 */
	private String code;
	
	/**
	 * 总页数
	 */
	private int totalCount;

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Result() {

	}
}
