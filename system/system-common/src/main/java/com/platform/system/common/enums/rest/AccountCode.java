package com.platform.system.common.enums.rest;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.platform.system.common.rest.RestStatus;

/**
 * 421XX 账户相关状态code
 * @version: 1.0
 */
public enum AccountCode implements RestStatus {
    
    /**
     * 用户不存在的情况
     */
    USER_NOT_EXISTS(42100, "用户不存在"),
    
    /**
     * 用于注册时用户已经存在的情况
     */
    USER_EXISTS(42101, "用户已经存在, 请尝试登录"),
    
    /**
     * 凭证错误
     */
    INVALID_CREDENTIAL(42102, "用户名或密码错误"),

    /**
     * 用户余额不足
     */
    INSUFFICIENT_BALANCE(42103, "用户余额不足"),
    /**
     * 用户绑定失败
     */
    BIND_USER_FAIL(42104, "用户绑定失败"),
    /**
     * 第三方用户不存在
     */
    THIRD_USER_NOT_EXISTS(42105, "第三方用户不存在"),
    /**
     * 用户解绑失败
     */
    UNBIND_USER_FAIL(42106, "用户解绑失败"),
    /**
     * 手机号错误
     */
    INVALID_PHONENO(42107, "手机号错误"),
    /**
     * 标签已存在
     */
    USER_LABEL_EXIST(42108, "标签名已存在"),
    /**
     * 标签名没有改变
     */
    USER_LABEL_UNUPDATE(42109, "标签名没有改变"),
        /**
     *赞助失败,你的粮票不足
     */
    RAISEFLOW_FAIL(42110, "赞助失败,你的粮票不足"),
    
    /**原密码输入错误 */
    OLDPASSWORD_ERROR(42111, "原密码输入错误"),
    /**
     * 账号被锁定
     */
    ACCOUNT_LOCKED(42112, "账号被锁定"),
    
    /**
     * 未设置支付密码
     */
    PAY_PASSWORD_NOT_SET(42113, "未设置支付密码"),
    
    /**
     * 支付密码不正确
     */
    PAY_PASSWORD_NOT_MATCHES(42114, "支付密码不正确"),
    
    /**
     * 第三方用户已绑定了其他账户
     */
    THIRD_USER_HAD_BIND_OTHER(42115, "第三方用户已绑定了其他账户"),

    /**
     * 商户/品牌积分: 已达到可领取积分的上限
     */
    JF_LIMIT_EXCEEDED(42116, "已达到可领取积分的上限"),
    ;

    private final int code;

    private final String message;
    
    private final boolean isLogStack;
    
    private static final ImmutableMap<Integer, AccountCode> CACHE;

    static {
        List<Integer> codeList = new ArrayList<Integer>();
        for (AccountCode statusCode : values()) {
            //防止code重复引起歧义
            if (codeList.contains(statusCode.code())){
                throw new IllegalStateException("statuscode重复>>" + statusCode);
            }
            codeList.add(statusCode.code());
        }
        
        final ImmutableMap.Builder<Integer, AccountCode> builder = ImmutableMap.builder();
        for (AccountCode statusCode : values()) {
            builder.put(statusCode.code(), statusCode);
        }
        CACHE = builder.build();
    }

    AccountCode(int code, String message) {
        this.code = code;
        this.message = message;
        this.isLogStack = false;
    }
    
    AccountCode(int code, String message, boolean isLogStack) {
        this.code = code;
        this.message = message;
        this.isLogStack = isLogStack;
    }

    /**
     * 根据code获取枚举
     * @param code
     * @return
     */
    public static AccountCode valueOfCode(int code) {
        final AccountCode status = CACHE.get(code);
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
    public boolean isLogErrorStack(){
        return isLogStack;
    }
}
