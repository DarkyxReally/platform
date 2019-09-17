package com.platform.system.common.web;

import com.platform.system.common.rest.RestStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName ResultMsg
 * @Description 接口结果类
 * @Version 1.0
 */
@Getter
@Setter
public class ResultMsg<T> {
    private boolean success;
    private String message;
    private RestStatus code;
    private T data;

    public ResultMsg() {

    }

    public ResultMsg(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public ResultMsg(boolean success, RestStatus code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public ResultMsg(boolean success, RestStatus code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
