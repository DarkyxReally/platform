package com.platform.system.common.json;

import org.apache.commons.lang3.StringUtils;

import com.platform.system.common.constant.Constants;
import com.platform.system.common.enums.rest.ErrorCode;

/**
 * JSONResult : Response JSONResult for RESTful,封装返回JSON格式的数据
 * @param <T>
 */
public class JSONResult<T> extends Result {

	private static final long serialVersionUID = 7880907731807860636L;

	/**
	 * 数据
	 */
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 默认正确响应
	 */
	public JSONResult() {
		super();
		super.setCode(ErrorCode.ERROR_100.getCode());
		super.setStatus(1);
		super.setTip("执行成功！");
	}
	/**
     * 默认正确响应，返回提示信息
     */
    public JSONResult(String tip) {
        super();
        super.setCode(ErrorCode.ERROR_100.getCode());
        super.setStatus(1);
        super.setTip(tip);
    }

	/**
	 * 错误响应
	 * 
	 * @param error
	 */
	public JSONResult(ErrorCode error) {
		super();
		super.setCode(error.getCode());
		super.setTip(error.getMsg());
		super.setStatus(0);
	}

	/**
	 * 错误响应和提示
	 * 
	 * @param error
	 */
	public JSONResult(ErrorCode error, String msg) {
		super();
		super.setCode(error.getCode());
		super.setTip(error.getMsg());
		if (msg != null) {
			super.setTip(msg);
		}
		super.setStatus(0);
	}

	/**
	 * 正确响应
	 * 
	 * @param t
	 */
	public JSONResult(T data) {
		super();
		super.setCode(ErrorCode.ERROR_100.getCode());
		super.setTip(ErrorCode.ERROR_100.getMsg());
		super.setStatus(1);
		this.data = data;
	}

	/**
	 * 正确响应
	 * 
	 * @param t
	 */
	public JSONResult(T data,int totalCount) {
		super();
		super.setCode(ErrorCode.ERROR_100.getCode());
		super.setTip(ErrorCode.ERROR_100.getMsg());
		super.setStatus(1);
		super.setTotalCount(totalCount);
		this.data = data;
	}
	/**
	 * 错误响应，要返回响应的数据
	 * <p>Title: </p> 
	 * <p>Description: </p> 
	 * @param error
	 * @param data2
	 */
    public JSONResult(ErrorCode error, T data2) {
        super();
        super.setCode(error.getCode());
        super.setTip(error.getMsg());
        if (data2 != null) {
            this.data=data2;
        }
        super.setStatus(0);
    }

    /**
     * 正确响应返回数据和提示信息
     * <p>Title: </p> 
     * <p>Description: </p> 
     * @param data2
     * @param tip
     */
    public JSONResult(T data2, String tip) {
        super();
        super.setCode(ErrorCode.ERROR_100.getCode());
        if(StringUtils.isNotBlank(tip)){
            super.setTip(tip);
        }else{
            super.setTip(ErrorCode.ERROR_100.getMsg());
        }
        super.setStatus(1);
        this.data = data2;
    }
	
    /**
	 * @param code
	 * @param msg
	 */
	public JSONResult(String code, String msg, T data) {
		super();
		super.setCode(code);
		super.setStatus(Constants.RET_SUCCESS.equals(code) ? 1 : 0);
//		super.setTip(msg == null? CodeFileConfig.getValue(this.getStatus() == 0 ? code: Constants.RET_SUCCESS) : msg);
		this.setData(data);
	}
	
	public JSONResult(Integer status, String code, String msg){
		super();
		super.setCode(code);
		super.setStatus(status);
		super.setTip(msg);
	}
	
	public JSONResult(Integer status, String code, String msg, T data){
		super();
		super.setCode(code);
		super.setStatus(status);
		super.setTip(msg);
		this.data = data;
	}
	
	/**
	 * @author liuq
	 * @param code
	 * @param msg
	 * @param totalCount
	 */
	public JSONResult(String code, String msg, T data,int totalCount) {
	    super();
	    super.setCode(code);
	    super.setStatus(Constants.RET_SUCCESS.equals(code) ? 1 : 0);
//	    super.setTip(msg == null? CodeFileConfig.getValue(this.getStatus() == 0 ? code: Constants.RET_SUCCESS) : msg);
	    super.setTotalCount(totalCount);
	    this.setData(data);
	}
	
	/**
	 * 新建失败结果对象
	 * @author Bob
	 * @param code
	 * @return
	 */
	public static <T> JSONResult<T> newFailureResult(String code, String msg) {
		return new JSONResult<T>(code, msg, null);
	}
	
	public static <T> JSONResult<T> newFailureResult(String msg) {
		return newFailureResult(Constants.RET_FAIL, msg);
	}
	
	
	/**
	 * 新建失败结果对象
	 * @author Bob
	 * @param code
	 * @return
	 */
	public static <T> JSONResult<T> newFailureResult(String code , T data) {
		return new JSONResult<T>(code, null, data);
	}
	
	
	
	/**
	 * 新建成功结果对象
	 * @author Bob
	 * @return
	 */
	public static <T> JSONResult<T> newSuccessResult() {
		return new JSONResult<T>(Constants.RET_SUCCESS, null, null);
	}

	/**
	 * 新建成功结果对象
	 * @author Bob
	 * @param result
	 * @return
	 */
	public static <T> JSONResult<T> newSuccessResult(T result) {
		return new JSONResult<T>(Constants.RET_SUCCESS, null, result);
	}

	/**
	 * 新建成功结果对象
	 * @author liuq
	 * @param result
	 * @return
	 */
	public static <T> JSONResult<T> newSuccessResult(T result,int totalCount) {
	    return new JSONResult<T>(Constants.RET_SUCCESS, null, result,totalCount);
	}
}