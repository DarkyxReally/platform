package com.platform.auth.model.innermodel.request;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Setter;
import lombok.Getter;

/**
 * rest请求操作日志
 * @version: 1.0
 */
@Getter
@Setter
public class RestOperatorLogRequest implements Serializable {

    private static final long serialVersionUID = 2390155019930965114L;
    
    /**
     * 请求id
     */
    @NotBlank(message="请求id不能为空")
    @Length(min=32, max=32, message="请求id不正确")
    private String idRequest;
    
    /**
     * 模块
     */
    @NotBlank(message="模块名称不能为空")
    @Length(max=20, message="模块名称太长")
    private String module;
    
    /**
     * 请求地址
     */
    @NotBlank(message="请求地址不能为空")
    @Length(max=1000, message="请求地址太长")
    private String url;
    
    /**
     * 请求方式
     */
    @NotBlank(message="请求方式不能为空")
    @Length(max=10, message="请求方式太长")
    private String method;
    
    /**
     * 请求参数
     */
    @NotBlank(message="请求参数不能为空")
    @Length(max=4000, message="请求参数太长")
    private String param;
    
    /**
     * 请求头
     */
    @NotBlank(message="请求头不能为空")
    @Length(max=4000, message="请求头太长")
    private String header;
    /**
     * 请求内容
     */
    private String body;
    
    /**
     * 请求时间
     */
    @NotNull(message="请求时间不能为空")
    private Date datetime;
    
    /**
     * 请求ip
     */
    @NotBlank(message="请求ip不能为空")
    @Length(max=255, message="请求ip太长")
    private String ip;
    
    /**
     * 用户IP
     */
    private String userIp;
    
    /**
     * 用户id
     */
    @Length(max=32, message="用户id不正确")
    private String idUser;
    
    /**
     * 系统code
     */
    @NotBlank(message="系统编码不能为空")
    @Length(max=255, message="系统编码太长")
    private String systemCode;
    
    /**
     * 服务名称
     */
    @NotBlank(message="服务名称不能为空")
    @Length(max=255, message="服务名称太长")
    private String serviceName;
    
    /**
     * 接口描述
     */
    @Length(max=255, message="接口描述太长")
    private String description;

}
