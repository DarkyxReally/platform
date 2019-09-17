package com.platform.auth.common.util;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 请求数据
 * @version: 1.0
 */
@Getter
@Setter
public class RequestData implements Serializable {

    private static final long serialVersionUID = -7891467934001414736L;
    
    /**
     * 请求id
     */
    private String idRequest;

    /**
     * 模块
     */
    private String module;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 请求参数
     */
    private String param;

    /**
     * 请求头
     */
    private String header;
    /**
     * 请求内容
     */
    private String body;

    /**
     * 请求时间
     */
    private Date datetime;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 用户IP
     */
    private String userIp;

    /**
     * 用户id
     */
    private String idUser;

    /**
     * 系统code
     */
    private String systemCode;

    /**
     * 接口描述
     */
    private String description;
}
