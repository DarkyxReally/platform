package com.platform.system.common.enums.rest;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.platform.system.common.rest.RestStatus;

/**
 * REST请求状态码
 *
 * @version: 1.0
 */
public enum StatusCode implements RestStatus {

    // 20xxx 请求正常
    /**
     * 20000 请求成功
     */
    OK(20000, "请求成功"),

    /**
     * 非法请求
     */
    INVALID_REQUEST(401, "非法请求"),
    /**
     * 页面不存在
     */
    PAGE_NOT_FOUND(404, "页面不存在"),

    // ######################## 400XX 客户端不合法的请求(请求参数非法或权限相关)##################
    /**
     * 字段校验非法
     */
    INVALID_MODEL_FIELDS(40001, "字段校验非法"),

    /**
     * 参数类型非法，常见于SpringMVC中String无法找到对应的enum而抛出的异常
     */
    INVALID_PARAMS_CONVERSION(40002, "参数类型非法"),

    /**
     * 权限不足
     */
    INVALID_PERMISSION_MISS(40003, "权限不足"),

    /**
     * 缺乏参数
     */
    PARAM_MISS(40004, "缺乏参数"),

    // ######################## 400XX 客户端不合法的请求(请求参数非法或权限相关)##################

    // ######################## 401XX 请求方式或请求头相关数据不正确 ###########################
    /**
     * HTTP消息不可读
     */
    HTTP_MESSAGE_NOT_READABLE(40101, "HTTP消息不可读"),
    /**
     * HTTP消息不可写
     */
    HTTP_MESSAGE_NOT_WRITABLE(40102, "HTTP消息不可写"),

    /**
     * 请求方式非法
     */
    REQUEST_METHOD_NOT_SUPPORTED(40103, "不支持的HTTP请求方法"),

    /**
     * 请求ContentType非法
     */
    REQUEST_CONTENT_TYPE_NOT_SUPPORTED(40104, "不支持的HTTP ContentType请求"),


    // ######################## 401XX 请求方式或请求头相关数据不正确 ###########################

    // ##################### 402XX 系统限制相关 #########################
    /**
     * 服务注册中, 请稍后再试
     */
    SERVICE_INITIALIZING(40200, "服务注册中, 请稍后再试"),

    /**
     * 数据重复
     */
    DUPLICATE_KEY(40201, "数据重复"),

    /**
     * 重复请求
     */
    DUPLICATE_REQUEST(40202, "正在处理中, 请勿重复操作或稍后再试"),

    /**
     * 请求频率达到限制
     */
    RATE_LIMIT_REQUEST(40203, "操作过快, 请勿频繁操作"),

    /**
     * 签名无效
     */
    INVALID_SIGN(40204, "签名无效"),

    /**
     * 重放攻击
     */
    REPLAY_ATTACKS(40205, "重放攻击"),

    /**
     * 当前版本过低,请升级版本
     */
    LOWER_VESION(40206, "当前版本过低,请升级版本"),

    /**
     * 操作失败
     */
    OPERATION_FAIL(40207, "操作失败", true),

    /**
     * XSS攻击
     */
    XSS_ATTACKS(40208, "请求含有XSS注入嫌疑"),

    // ##################### 402XX 系统限制相关 #########################

    // ######################## 420XX 相关业务通用状态 ################################
    /**
     * 数据不存在
     */
    DATA_NOT_EXISTS(42000, "数据不存在"),

    /**
     * 数据添加失败
     */
    DATA_ADD_FAIL(42001, "数据添加失败"),

    /**
     * 数据修改失败
     */
    DATA_UPDATE_FAIL(42002, "数据修改失败"),

    /**
     * 数据删除失败
     */
    DATA_DELETE_FAIL(42003, "数据删除失败"),
    /**
     * 配置错误
     */
    DATE_CONFIG_FAIL(42004, "配置错误"),

    /**
     * 数据已存在
     */
    DATA_EXISTS(42005, "数据已存在"),

    /**
     * 主键重复
     */
    DUPLICATE_PRIMARY(42006, "主键重复"),

    /**
     * 授权码已失效
     */
    AUTHCODE_INVALID(42007, "授权码已失效"),

    /**
     * 已领取
     */
    HAVE_TO_RECEIVE(42008, "已领取", true),

    /**
     * 已抢完
     */
    COUPON_TAKE_UP(42009, "已抢完", true),
    
    /**
     * 登录账号为手机号或邮箱
     */
    LOGIN_NAME_NOT_REGULAR(40210,"登录账号为手机号或邮箱",true),
    
    /**
     * 登录密码为英文字母+数字，最短9位，最长20位
     */
    PASSWORD_NOT_REGULAR(40211,"登录密码为英文字母+数字，最短9位，最长20位",true),
    
    /**
     * 登录账号已存在
     */
    LOGIN_NAME_IS_EXISTING(40212,"账号已存在",true),
    
    /**
     * 收银员手机号已存在
     */
    CASHIER_PHONE_IS_EXISTING(40213,"收银员手机号已存在",true),

    // ########################420XX 相关业务通用状态################################

    // 50xxx 服务端异常
    /**
     * 用于处理未知的服务端错误
     */
    SERVER_UNKNOWN_ERROR(50001, "服务端异常, 请稍后再试", true),

    /**
     * 用于远程调用时的系统出错
     */
     SERVER_IS_BUSY_NOW(50002, "系统繁忙, 请稍后再试", true),
    /**
     * 第三方远程调用失败
     */
    SERVER_THIRD_CALL_FAIL(50003, "第三方远程调用失败", true),
    /**
     * 运行时异常
     */
    SERVER_RUNTIME_EXCEPTION(50004, "运行时发生异常", true),
    /**
     * 空指针异常
     */
    SERVER_NULL_POINTER_EXCEPTION(50005, "空指针异常", true),
    /**
     * IndexOutOfBoundsException
     */
    SERVER_INDEX_OUT_OF_BOUNDS_EXCEPTION(50006, "数组越界异常", true),
    /**
     * NoSuchMethodException
     */
    SERVER_NO_SUCH_METHOD_EXCEPTION(50007, "没有对应方法异常", true),

    /**
     * IOException IO流异常
     */
    SERVER_IO_EXCEPTION_EXCEPTION(50008, "IO流异常", true),

    /**
     * 类型转换异常
     */
    SERVER_CLASSCAST_EXCEPTION(50009, "类型转换异常", true),

    /**
     * 流程操作异常
     */
    SERVER_OPERATION__EXCEPTION(50010, "流程操作异常", true),
    
    /**
	 * 参数不正确
	 */
	RET_PARAMERROR(102,"参数格式不正确"),
	
	/**
	 * 登录失败
	 */
	LOGIN_FAIL(103,"登录失败"),
	
	/**
	 * 下载链接已失效
	 */
	LINK_FAILURE(104,"下载链接已失效"),
	
	/**
	 * 文件不存在
	 */
	FILE_NOTEXIST(105,"文件不存在"),

    /**
     * 用户资料不完善
     */
    USER_DATA_NOT_PERFECT(50015,"用户资料不完善",true),
    
	/**
	 * 用户资料不完善
	 */
	BOOTH_NOT_ATTACH(50016,"展商展位资料不存在",true),
	;

    private final int code;

    private final String message;

    private final boolean isLogStack;

    private static final ImmutableMap<Integer, StatusCode> CACHE;

    static {
        List<Integer> codeList = new ArrayList<Integer>();
        for (StatusCode statusCode : values()) {
            // 防止code重复引起歧义
            if (codeList.contains(statusCode.code())) {
                throw new IllegalStateException("statuscode重复>>" + statusCode);
            } else {
                codeList.add(statusCode.code());
            }
        }

        final ImmutableMap.Builder<Integer, StatusCode> builder = ImmutableMap.builder();
        for (StatusCode statusCode : values()) {
            builder.put(statusCode.code(), statusCode);
        }
        CACHE = builder.build();
    }

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
        this.isLogStack = false;
    }

    StatusCode(int code, String message, boolean isLogStack) {
        this.code = code;
        this.message = message;
        this.isLogStack = isLogStack;
    }

    /**
     * 根据code获取枚举
     *
     * @param code
     * @return
     */
    public static StatusCode valueOfCode(int code) {
        final StatusCode status = CACHE.get(code);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }
        return status;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public boolean isLogErrorStack() {
        return isLogStack;
    }
}
