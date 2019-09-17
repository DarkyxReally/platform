package com.platform.auth.model.model.inner;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户环境信息
 * @version: 1.0
 */
@Getter
@Setter
public class UserEnvironment implements Serializable {

    private static final long serialVersionUID = 2319310096204841141L;

    /**
     * 设备识别码
     */
    private String idDevice;

    /**
     * 登陆设备机型(华为，三星等等)
     */
    private String mobileModel;
    /**
     * 设备类型(1:安卓;2:ios)
     */
    private String platform;
    /**
     * 版本识别码
     */
    private String version;

    /**
     * 用户IP
     */
    private String userIp;

    /**
     * 地点
     */
    private String address;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;
}
