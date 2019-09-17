package com.platform.rabbitmq.model.api.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.platform.rabbitmq.model.api.validate.JPushBindingRequest;

import lombok.Getter;
import lombok.Setter;

/**
 * 通知绑定请求
 * @version: 1.0
 */
@Getter
@Setter
public class NoticeBindingRequest implements Serializable {

    private static final long serialVersionUID = 6785694088341627651L;

    /**
     * 注册id
     */
    @NotBlank(groups = {JPushBindingRequest.class}, message = "注册ID不能为空")
    @Length(groups = {JPushBindingRequest.class}, min=16, max=255, message = "注册ID长度不正确")
    private String registrationID;
    
    /**
     * 绑定的别名
     */
    @NotBlank(groups = {JPushBindingRequest.class}, message = "别名不能为空")
    @Length(groups = {JPushBindingRequest.class}, min=11, max=11, message = "别名长度不正确")
    private String alias;
    
    /**
     * 华为手机绑定ID
     */
    private String idToken;
}
