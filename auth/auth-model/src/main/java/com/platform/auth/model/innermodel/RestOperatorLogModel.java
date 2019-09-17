package com.platform.auth.model.innermodel;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * REST操作请求日志模型
 * @version: 1.0
 */
@Getter
@Setter
public class RestOperatorLogModel implements Serializable{

    private static final long serialVersionUID = -7561420570969940487L;
    
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
     * 用户ip
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
     * 服务名称
     */
    private String serviceName;
    
    /**
     * 接口描述
     */
    private String description;
}
